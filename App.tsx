import React, { useState, useEffect } from 'react';
import { NativeModules, Button, Text, View, DeviceEventEmitter } from 'react-native';

const { PowerInfoModule } = NativeModules;

type PowerInfo = {
  current: number;
  voltage: number;
  chargerType: string;
  isCharging: boolean;
  health: string;
};

function App() {
  const [powerInfo, setPowerInfo] = useState<PowerInfo | null>(null);  // Explicit type declaration
  const [error, setError] = useState<string | null>(null);  // Explicit type declaration

  const fetchPowerInfo = () => {
    PowerInfoModule.getPowerInfo()
      .then((result: PowerInfo) => {
        setPowerInfo(result);
        console.log('Power Info:', result);
      })
      .catch((err: Error) => {
        setError('Error fetching power info');
        console.error(err);
      });
  };

  useEffect(() => {
    fetchPowerInfo(); // Initial fetch on load

    // Listen for power connection/disconnection events
    const powerConnectedListener = DeviceEventEmitter.addListener('POWER_CONNECTED', () => {
      console.log('Charger connected');
      fetchPowerInfo(); // Fetch power info when charger is connected
    });

    const powerDisconnectedListener = DeviceEventEmitter.addListener('POWER_DISCONNECTED', () => {
      console.log('Charger disconnected');
      fetchPowerInfo(); // Fetch power info when charger is disconnected
    });

    // Cleanup listeners on component unmount
    return () => {
      powerConnectedListener.remove();
      powerDisconnectedListener.remove();
    };
  }, []);

  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center', backgroundColor: '#000' }}>
      <Text style={{ color: 'white', fontSize: 24 }}>Power Info</Text>
      {error ? <Text style={{ color: 'white' }}>{error}</Text> : null}
      {powerInfo ? (
        <View>
          <Text style={{ color: 'white' }}>Current: {powerInfo.current} mA</Text>
          <Text style={{ color: 'white' }}>Voltage: {powerInfo.voltage} V</Text>
          <Text style={{ color: 'white' }}>Charger Type: {powerInfo.chargerType}</Text>
          <Text style={{ color: 'white' }}>Charging: {powerInfo.isCharging ? 'Yes' : 'No'}</Text>
          <Text style={{ color: 'white' }}>Health: {powerInfo.health}</Text>
        </View>
      ) : (
        <Text style={{ color: 'white' }}>Loading...</Text>
      )}
      <Button title="Refresh Power Info" onPress={fetchPowerInfo} />
    </View>
  );
}

export default App;
