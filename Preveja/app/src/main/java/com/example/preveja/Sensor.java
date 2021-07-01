package com.example.preveja;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

//--------------------------------SENSOR DE LUMINOSIDADE--------------------------------//

public class Sensor extends AppCompatActivity implements SensorEventListener{

    SensorManager sensorManager;
    android.hardware.Sensor sensor;
    TextView txtSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        //-------APLICAÇÃO DO SENSOR-------//

        sensorManager = (SensorManager)getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_LIGHT);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener((SensorEventListener)this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()== android.hardware.Sensor.TYPE_LIGHT){
            //AMBIENTE ESCURO
            if(event.values[0]==0){
                Toast.makeText(this, "Diminua o brilho do celular", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Aumente o brilho do celular", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class TYPE_LIGHT {
    }
}
