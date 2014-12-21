package com.example.PushApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity implements SensorEventListener{
    /**
     * Called when the activity is first created.
     */
    TextView counter;
    TextView revcount;
    public boolean wh = false;
    public int many = 0;
    public int max;
    SensorManager manager;
    ReadFromFile read = new ReadFromFile();
    WriteToFile write = new WriteToFile();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        max = read.read();
        if(max<0){
            max = 0;
        }
        counter = (TextView)findViewById(R.id.counter);
        revcount = (TextView)findViewById(R.id.headline);
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
            if(max-many>0) {
                revcount.setText("Just a " + (max - many) + " push ups to beat your record(" + max + ")");
            }
            else{
                revcount.setText("Keep it up, you are " + (-(max-many)) + " above your record");
            }

            counter.setText(String.valueOf(many));

        }
    }
    public void endOfExercise(View view){
        AlertDialog d = new AlertDialog.Builder(this).create();
        d.setTitle("Record dialog");
        d.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        d.setIcon(R.drawable.ic_launcher);

        if(many>max) {
            write.write(many);
            d.setMessage("Your previous record(" + max + ") was overwrited with your performance(" + many + ")");
            max = many;
        }
        else{
            d.setMessage("Well done! Too bad, you could not beat yourself. You were " + (max-many) + " push ups below record");
        }
        many = 0;
        counter.setText(String.valueOf(many));
        revcount.setText("Just a " + (max - many) + " push ups to beat your record(" + max + ")");
        d.show();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
