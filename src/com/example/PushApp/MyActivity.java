package com.example.PushApp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity implements SensorEventListener{
    /**
     * Called when the activity is first created.
     */
    TextView counter;
    public boolean wh = false;
    public int many = 0;
    SensorManager manager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        counter = (TextView)findViewById(R.id.counter);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sen = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(sen==null){
            Toast.makeText(this, "You have no proximity sensor", Toast.LENGTH_LONG).show();
            finish();
        }

    }
    protected void onPause(){
        super.onPause();
        manager.unregisterListener(this);
    }
    protected void onResume(){
        super.onResume();
        manager.registerListener(this, manager.getDefaultSensor(Sensor.TYPE_PROXIMITY), manager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()== Sensor.TYPE_PROXIMITY){
            if(event.values[0]>5 && wh){
                wh = false;
            }
            else if(event.values[0]<5 && !wh){
                wh = true;
                many++;
            }
            counter.setText(String.valueOf(many));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
