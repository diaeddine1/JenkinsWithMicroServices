import React, { useState, useEffect } from 'react';
import { View, Image, Text, StyleSheet, TouchableOpacity, FlatList, Modal, Clipboard } from 'react-native';
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5';
import { useRoute } from '@react-navigation/native';
import axios from 'axios';
import QRCode from 'react-native-qrcode-svg';


const AfterLoginScreen = ({ navigation }) => {
  const route = useRoute();
  const userId = route.params.userId;
  const [userHistory, setUserHistory] = useState([]);
  const [selectedItem, setSelectedItem] = useState(null);

  useEffect(() => {
    const fetchData = () => {
      const requestData = {
        userId:  parseInt(userId, 10)
      };

      axios.post('http://192.168.11.110:9776/history/getall', requestData)
        .then((response) => {
          // Reverse the user history array before setting it in the state
          const reversedUserHistory = response.data.reverse();
          setUserHistory(reversedUserHistory);
        })
        .catch((error) => {
          console.error('Error fetching user history:', error);
        });
    };

    // Fetch data initially
    fetchData();

    // Set up interval to fetch data every 2 seconds
    const interval = setInterval(() => {
      fetchData();
    }, 4000);

    // Cleanup the interval when the component unmounts 
    return () => clearInterval(interval);
  }, [userId]);

  const openPopup = (item) => {
    setSelectedItem(item);
  };

  const closePopup = () => {
    setSelectedItem(null);
  };

  const copyToClipboard = (textToCopy) => {
    Clipboard.setString(textToCopy);
    closePopup();
  };

    
  return (
    <View style={styles.container}>
      <View style={styles.upperRow}>
        <View style={styles.leftColumn}>
        <TouchableOpacity style={styles.button1} onPress={() => {navigation.navigate('ScanScreen', { userId: userId }); }}>
            <FontAwesome5 name="camera" style={styles.icon1} />
          </TouchableOpacity>
        </View>
        <View style={styles.middleColumn}>
        <TouchableOpacity onPress={() => {navigation.navigate('ProfileScreen', { userId: userId }); }}>
            <Image
              source={require('../images/profile.png')}
              style={styles.circularImage}
            />
          </TouchableOpacity>
        </View>
        <View style={styles.rightColumn}>
          <TouchableOpacity style={styles.button2} onPress={() => {navigation.navigate('GenerateScreen', { userId: userId }); }}>
            <FontAwesome5 name="qrcode" style={styles.icon1} />
          </TouchableOpacity>
        </View>
      </View>
      <View style={styles.lowerRow}>
        <FlatList
          data={userHistory}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <TouchableOpacity onPress={() => openPopup(item)}>
              <View style={styles.cardContainer}>
                <View style={styles.leftColumn1}>
                  <FontAwesome5 name="copy" size={23} color="#ec8619" />
                </View>
                <View style={styles.rightColumn2}>
                  <Text style={styles.cardText1}>{item.action}</Text>
                  <Text style={styles.cardText2}>{item.timestamp}</Text>
                  <Text style={styles.cardText3}>{item.qrCode.data}</Text>
                </View>
              </View>
            </TouchableOpacity>
          )}
        />
      </View>

      {selectedItem && (
        <Modal animationType="slide" transparent={true}>
          <View style={styles.popupContainer}>
            <View style={styles.popupContent}>
              <TouchableOpacity style={styles.closeButton} onPress={closePopup}>
                <FontAwesome5 name="times" style={styles.closeIcon} />
              </TouchableOpacity>
              <Text style={styles.popupText3}>{selectedItem.qrCode.data}</Text>
              <TouchableOpacity
                style={styles.copyButton}
                onPress={() => copyToClipboard(selectedItem.qrCode.data)}
              >
                 <FontAwesome5 name="copy" size={23} color="white" />
              </TouchableOpacity>
              <View style={styles.qrcode}>
              <QRCode  value={selectedItem.qrCode.data} size={250} />
              </View>
            </View>
          </View>
        </Modal>
      )}
    </View>
  );
};
const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  upperRow: {
    paddingTop: 45,
    flex: 0.07,
    flexDirection: 'row',
    backgroundColor: 'white',
    padding: 16,
    justifyContent: 'center',
  },
  leftColumn: {
    flex: 0.3,
    alignItems: 'flex-start',
  },
  middleColumn: {
    marginLeft: 6,
    flex: 0.4,
    justifyContent: 'center',
    alignItems: 'center',
  },
  middleText1: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#202c4c',
  },
  middleText2: {
    fontSize: 10,
    color: '#202c4c',
    fontWeight: 'bold',
  },
  rightColumn: {
    flex: 0.3,
    justifyContent: 'center',
    alignItems: 'flex-end',
  },
  image: {
    width: '85%',
    height: '100%',
  },
  button1: {
    width: 55,
    backgroundColor: 'white',
    padding: 10,
    borderRadius: 5,
    justifyContent: 'center',
    alignItems: 'center',
  },
  button2: {
    width: 55,
    backgroundColor: 'white',
    padding: 10,
    borderRadius: 5,
    justifyContent: 'center',
    alignItems: 'center',
  },
  buttonText: {
    color: 'white',
    fontSize: 15,
  },
  lowerRow: {
    flex: 0.93,
    backgroundColor: 'white', 
    
   },
  lowerRowContent1: {
    borderRadius : 20,
    marginLeft : 15,
    marginBottom : 15,
    marginTop : 30,
    width : 380,
    backgroundColor : '#f1f3f4',
    flex: 0.4,
    flexDirection: 'row',
    alignItems: 'center',
  },
  iconContainer: {
    justifyContent : 'center',
    alignItems : 'center',
    borderRadius : 50,
    width : 100,
    height : 100,
    marginLeft : 20,
  },
  icon1: {
    fontSize: 25,
    color: '#2074d4',
  },
  textContainer: {
    flex: 0.8,
  },
  lowerRowText: {
    fontSize: 20,
    color: '#2074d4',
    fontWeight: 'bold',
  },
  circularImage: {
    borderWidth :3,
    borderColor : '#2074d4',
    width: 50,
    height: 50,
    borderRadius: 1000,
    backgroundColor: 'grey',
  },
  cardContainer: {
    padding : 23,
    marginBottom : 20,
   
  },
  leftColumn1: {
    flex: 0.3,
  },
  rightColumn2: {
    flex: 0.7, 
    
  },
  cardText1: {
    alignSelf : 'flex-start',
   marginTop : 10 ,
   marginLeft : 2,
    fontSize: 20,
    color: '#2074d4',
    fontWeight: 'bold',
  },
  cardText2: {
    alignSelf : 'flex-start',
   marginTop : 10 ,
   marginLeft : 2,
    fontSize: 20,
    color: '#ec8619',
    fontWeight: 'bold',
  },
  
  cardText3: {
    alignSelf : 'flex-end',
   marginTop : 10 ,
   marginRight: 5,
    fontSize: 25,
    color: 'black',
    fontWeight: 'bold',
  },
  popupContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  popupContent: {
    backgroundColor: 'white',
    padding: 20,
    borderRadius: 10,
    borderWidth : 3,
    borderColor : '#2074d4',
    width: '80%',
    height : '60%',
  },
  closeButton: {
    position: 'absolute',
    top: 5,
    right: 12,
  },
  closeIcon: {
    fontSize: 30,
    color: '#ec8619',
  },
  popupText1: {
    fontSize: 20,
    color: '#2074d4',
    fontWeight: 'bold',
  },
  popupText2: {
    fontSize: 20,
    color: '#ec8619',
    fontWeight: 'bold',
  },
  popupText3: {
    fontSize: 25,
    color: 'black',
    fontWeight: 'bold',
    alignSelf : 'center',
    marginTop :20,
  },
  copyButton: {
    alignItems: 'center',
    backgroundColor: '#2074d4',
    borderRadius : 12,
    width : 35,
    height : 35,
    justifyContent : 'center',
    position: 'absolute',
    top: 8,
    right: 240,  
  },
  copyButtonText: {
    fontSize : 20,
    color: 'white',
    fontWeight : 'bold',
  },
  qrcode: {
  marginTop :30,
  justifyContent : 'center',
  alignSelf : 'center',
  borderWidth : 5,
  borderColor : '#ec8619',
  padding : 5,
  },

});
export default AfterLoginScreen;