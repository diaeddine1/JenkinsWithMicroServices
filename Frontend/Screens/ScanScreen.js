import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { BarCodeScanner } from 'expo-barcode-scanner';
import { useRoute, useNavigation } from '@react-navigation/native';
import axios from 'axios'; // Import Axios

const ScanScreen = () => {
  const route = useRoute();
  const navigation = useNavigation();
  const userId = route.params.userId;
  const [hasPermission, setHasPermission] = useState(null);
  const [scanned, setScanned] = useState(false);
  const [data, setData] = useState(null);

  useEffect(() => {
    (async () => {
      const { status } = await BarCodeScanner.requestPermissionsAsync();
      setHasPermission(status === 'granted');
    })();
  }, []);

  const handleBarCodeScanned = async ({ type, data }) => {
    setScanned(true);
    setData(data);

    // Perform the Axios POST request
    try {
      const url = 'http://192.168.11.110:9776/qrcodes/scan';
      const payload = {
        id: userId,
        data: data,
      };

      const response = await axios.post(url, payload);
      // Handle the response as needed
      console.log('POST request response:', response.data);

      // Navigate to AfterLoginScreen and pass the userId
      navigation.navigate('AfterLoginScreen', { userId: userId });
    } catch (error) {
      console.error('POST request error:', error);
    }
  };

  if (hasPermission === null) {
    return <Text>Requesting camera permission</Text>;
  }
  if (hasPermission === false) {
    return <Text>No access to camera</Text>;
  }

  return (
    <View style={styles.container}>
      <View style={StyleSheet.absoluteFill}>
        <BarCodeScanner
          onBarCodeScanned={scanned ? undefined : handleBarCodeScanned}
          style={StyleSheet.absoluteFill}
        />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default ScanScreen;
