import React, { useState, useEffect } from 'react'; 
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import LoadingScreen from './Screens/LoadingScreen';
import LoginScreen from './Screens/LoginScreen';
import SignupScreen from './Screens/SignupScreen';
import AfterLoginScreen from './Screens/AfterLoginScreen';
import ScanScreen from './Screens/ScanScreen';
import GenerateScreen from './Screens/GenerateScreen';
import ProfileScreen from './Screens/ProfileScreen';


const Stack = createStackNavigator();

function App() {
  const [isLoading, setIsLoading] = useState(true);

  
  useEffect(() => {
    const delay = 3000; 
    setTimeout(() => {
      setIsLoading(false);
    }, delay);
  }, []);

  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Home">
        {isLoading ? (
          <Stack.Screen name="LoadingScreen" component={LoadingScreen} options={{ headerShown: false }} />
        ) : (
          <Stack.Screen name="LoginScreen" component={LoginScreen} options={{ headerShown: false }} />
          
        )}
          <Stack.Screen name="SignupScreen" component={SignupScreen} options={{ headerShown: false }} />
          <Stack.Screen name="AfterLoginScreen" component={AfterLoginScreen} options={{ headerShown: false }} />
          <Stack.Screen name="ScanScreen" component={ScanScreen} options={{ headerShown: false }} />
          <Stack.Screen name="GenerateScreen" component={GenerateScreen} options={{ headerShown: false }} />
          <Stack.Screen name="ProfileScreen" component={ProfileScreen} options={{ headerShown: false }} />

     

      </Stack.Navigator>
    </NavigationContainer>
  );
}

export default App;
