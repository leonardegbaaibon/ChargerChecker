package com.chargerchecker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.Arguments
import android.util.Log


class PowerInfoModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    private val powerConnectionReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_POWER_CONNECTED || intent.action == Intent.ACTION_POWER_DISCONNECTED) {
                fetchPowerInfo()
            }
        }
    }

    init {
        val filter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        reactApplicationContext.registerReceiver(powerConnectionReceiver, filter)
    }

    override fun getName(): String = "PowerInfoModule"

    @ReactMethod
    fun getPowerInfo(promise: Promise) {
        fetchPowerInfo(promise)
    }

    private fun fetchPowerInfo(promise: Promise? = null) {
        try {
            val batteryManager =
                reactApplicationContext.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

            // Attempt to get current using BatteryManager properties
            val current = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)

            // Fallback to Intent.ACTION_BATTERY_CHANGED for voltage and additional details
            val intent: Intent? = reactApplicationContext.registerReceiver(
                null,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )

            // Extract fallback data
            val voltage = intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) ?: -1
            val plugged = intent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1
            val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
            val health = intent?.getIntExtra(BatteryManager.EXTRA_HEALTH, -1) ?: -1

            // Determine charger type
            val chargerType = when (plugged) {
                BatteryManager.BATTERY_PLUGGED_AC -> "AC"
                BatteryManager.BATTERY_PLUGGED_USB -> "USB"
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> "Wireless"
                else -> "Unknown"
            }

            // Determine charging status
            val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING

            // Determine battery health
            val healthStatus = when (health) {
                BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
                BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
                BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
                BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Failure"
                else -> "Unknown"
            }

            // Convert values to readable formats
            val currentInMilliamps = current / 1000 // µA to mA
            val voltageInVolts = voltage / 1000.0 // mV to V

            // Create WritableMap for returning data
            val map: WritableMap = Arguments.createMap().apply {
                putDouble("current", currentInMilliamps.toDouble())
                putDouble("voltage", voltageInVolts)
                putString("chargerType", chargerType)
                putBoolean("isCharging", isCharging)
                putString("health", healthStatus)
            }

            // If promise exists, resolve it
            promise?.resolve(map)

            Log.d("Power Info", map.toString())
        } catch (e: Exception) {
            // Reject the promise in case of an error
            promise?.reject("ERROR", "Failed to get power info: ${e.message}")
        }
    }

    override fun onCatalystInstanceDestroy() {
        // Unregister the receiver when the module is destroyed
        reactApplicationContext.unregisterReceiver(powerConnectionReceiver)
        super.onCatalystInstanceDestroy()
    }
}
