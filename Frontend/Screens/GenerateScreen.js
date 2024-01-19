import React, { useState } from 'react';
import { View, Text, TextInput, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import QRCode from 'react-native-qrcode-svg';
import axios from 'axios';
import { useRoute } from '@react-navigation/native';

const GenerateScreen = ({ navigation }) => {
  const [text, setText] = useState('');
  const route = useRoute();
  const userId = route.params.userId;

  const saveQRCode = () => {
    const url = 'http://192.168.11.110:9776/qrcodes/generate';

    const payload = {
      id: userId,
      data: text,
    };

    axios
      .post(url, payload)
      .then(response => {
        // Handle the successful response here
        console.log('Request successful:', response.data);

        navigation.navigate('AfterLoginScreen', { userId: userId });
      })
      .catch(error => {
        // Handle any errors here
        console.error('Error:', error);
      });
  };

  return (
    <View style={styles.container}>
      <View style={styles.lowerRowContent1}>
        <ScrollView style={styles.textInputContainer}>
          <TextInput
            style={styles.textInput}
            placeholder="Type your text here"
            multiline={true}
            value={text}
            onChangeText={(newText) => setText(newText)}
          />
        </ScrollView>
      </View>
      <View style={styles.qrCodeContainer}>
        {text ? (
          <QRCode value={text} size={250} />
        ) : (
          <Text></Text>
        )}
      </View>
      <View style={styles.ButtonContainer}>
        {text ? (
          <TouchableOpacity style={styles.button} onPress={saveQRCode}>
            <Text style={styles.buttonText}>Save</Text>
          </TouchableOpacity>
        ) : null}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
  },
  lowerRowContent1: {
    padding: 10,
    borderRadius: 20,
    marginLeft: 15,
    marginBottom: 5,
    marginTop: 40,
    width: 330,
    backgroundColor: '#f1f3f4',
    flex: 0.2,
  },
  textInputContainer: {
    flex: 1,
  },
  textInput: {
    flex: 1,
    paddingHorizontal: 10,
  },
  qrCodeContainer: {
    alignItems: 'center',
    marginTop: 5,
    flex : 0.6,
    justifyContent : 'center',
    alignItems : 'center',
  },
  ButtonContainer : {
    flex : 0.2,
    justifyContent : 'center',
    alignItems : 'center',
  },
  button: {
    borderRadius: 10,
    marginTop: 65,
    backgroundColor: 'blue',
    width: 380,
    padding: 20,
    alignItems: 'center',
    backgroundColor: '#08bcfc',
  },
  buttonText: {
    color: 'white', 
    fontSize: 20,
    fontWeight : 'bold',
  },
});

export default GenerateScreen;
