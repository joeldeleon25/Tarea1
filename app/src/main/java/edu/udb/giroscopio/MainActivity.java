package edu.udb.giroscopio;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.sql.DriverPropertyInfo;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    int whip=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor==null)
            finish();

        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x=sensorEvent.values[0];
                System.out.println("valor giro "+ x);
                if (x<-5 && whip==0){
                    whip++;
                    getWindow().getDecorView().setBackground(Color.BLUE);
                }else if (x>5 && whip==1){
                    whip++;
                    getWindow().getDecorView().setBackground(Color.RED);
                }

                if (whip==2){
                    sound();
                    whip=0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        star();

    }

    private void sound(){
        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.latigo);
        mediaPlayer.start();
    }

    private void star() {
        sensorManager.registerListener(sensorEventListener,sensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop() {
        sensorManager.unregisterListener(sensorEvent);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        star();
        super.onResume();
    }
}