import React, { useState } from 'react';
import { View, Image, TextInput, Text, StyleSheet, TouchableOpacity, Alert } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { useRoute } from '@react-navigation/native';
import axios from 'axios';

const SignupScreen = ({ navigation }) => {
  const route = useRoute();
  const userId = route.params.userId;

  const [passwordVisible, setPasswordVisible] = useState(false);
  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');

  const togglePasswordVisibility = () => {
    setPasswordVisible(!passwordVisible);
  };

  const handleUpdate = () => {
    if (oldPassword.trim() === '' || newPassword.trim() === '') {
      Alert.alert('Please fill in both old and new passwords.');
    } else if (newPassword.length < 8) {
      Alert.alert('New password must be at least 8 characters long.');
    } else {
      axios
        .post('http://192.168.11.110:9776/users/updatepassword', {
          userId : userId,
          oldpassword: oldPassword,
          newpassword: newPassword,
        })
        .then((response) => {
          if (response.data === 'Password updated successfully') {
            Alert.alert('Password updated successfully');
            setOldPassword(''); 
            setNewPassword('');
            navigation.navigate('AfterLoginScreen', { userId: userId });
          } else {
            Alert.alert('Old password is incorrect');
          }
        })
        .catch((error) => {
          console.error('Password update failed:', error);
          Alert.alert('Password update failed. Please try again.');
        });
    }
  };


  return (
    <View style={styles.container}>
      <View style={styles.upperRow}>
        <Image source={require('../images/profile.png')} style={styles.image} />
      </View>
      <View style={styles.lowerRow}>
        <View style={styles.formGroup}>
          <Text style={styles.label}>Old Password</Text>
          <View style={styles.passwordInput}>
            <TextInput
              style={styles.passwordTextInput}
              secureTextEntry={!passwordVisible}
              placeholder="* * * * * * * * * * * * *"
              value={oldPassword}
              onChangeText={setOldPassword}
            />
            <TouchableOpacity onPress={togglePasswordVisibility} style={styles.togglePasswordButton}>
              <Icon name={passwordVisible ? 'eye-slash' : 'eye'} size={20} color="#000" />
            </TouchableOpacity>
          </View>
        </View>

        <View style={styles.formGroup}>
          <Text style={styles.label}>New Password</Text>
          <View style={styles.passwordInput}>
            <TextInput
              style={styles.passwordTextInput}
              secureTextEntry={!passwordVisible}
              placeholder="* * * * * * * * * * * * *"
              value={newPassword}
              onChangeText={setNewPassword}
            />
            <TouchableOpacity onPress={togglePasswordVisibility} style={styles.togglePasswordButton}>
              <Icon name={passwordVisible ? 'eye-slash' : 'eye'} size={20} color="#000" />
            </TouchableOpacity>
          </View>
          <TouchableOpacity style={styles.button} onPress={handleUpdate}>
            <Text style={styles.buttonText}>Update</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.button2} onPress={() => {navigation.navigate('LoginScreen');}}>
            <Text style={styles.buttonText2}>Logout</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );
};
  
  const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#ff7424',
    },
    upperRow: {
      flex: 0.30,
      backgroundColor: 'white',
      justifyContent: 'center',
      alignItems: 'center',
    },
    lowerRow: {
      flex: 0.7,
      backgroundColor: 'white',
    },
    image: {
      marginTop : 8,
      aspectRatio: 1,
      flex: 0.7,
      resizeMode: 'contain',
      justifyContent: 'center',
      alignItems: 'center',
    },
    formGroup: {
      margin: 20,
    },
    label: {
      fontSize: 16,
      margin: 3,
      color: 'black',
      fontWeight: 'bold',
    },
    passwordInput: {
      marginTop: 10,
      backgroundColor: '#F6F6F7',
      flexDirection: 'row',
      alignItems: 'center',
      borderRadius: 20,
      padding: 10,
    },
    passwordTextInput: {
      flex: 1,
    },
    togglePasswordButton: {
      padding: 5,
    },
    button: {
      backgroundColor: '#08bcfc',
      borderRadius: 20,
      padding: 10,
      alignItems: 'center',
      height: 70,
      marginTop: 40,
      justifyContent: 'center',
      alignItems: 'center',
    },
    button2: {
      backgroundColor: '#FB0440',
      borderRadius: 20,
      padding: 10,
      alignItems: 'center',
      height: 70,
      marginTop: 40,
      justifyContent: 'center',
      alignItems: 'center',
    },
    buttonText: {
      color: 'white',
      fontSize: 24,
      justifyContent: 'center',
      alignItems: 'center',
      fontWeight: 'bold',
    },
    buttonText2: {
      color: 'white',
      fontSize: 24,
      justifyContent: 'center',
      alignItems: 'center',
      fontWeight: 'bold',
    },
  
   
  });
  

  

export default SignupScreen;




