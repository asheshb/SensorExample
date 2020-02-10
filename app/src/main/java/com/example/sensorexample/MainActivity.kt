package com.example.sensorexample

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var temperatureSensor: Sensor? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)


        if(temperatureSensor == null){
            ambient_temperature.text = getString(R.string.temperature_sensor_not_found)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        //Accuracy constants:
        // SENSOR_STATUS_ACCURACY_LOW,
        // SENSOR_STATUS_ACCURACY_MEDIUM,
        // SENSOR_STATUS_ACCURACY_HIGH,
        // SENSOR_STATUS_UNRELIABLE.
    }

    override fun onSensorChanged(event: SensorEvent) {
        ambient_temperature.text = getString(R.string.ambient_temperature, event.values[0])
    }

    override fun onResume() {
        super.onResume()
        temperatureSensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
