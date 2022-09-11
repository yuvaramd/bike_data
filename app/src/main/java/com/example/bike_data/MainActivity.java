package com.example.bike_data;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private  static  int MICROPHONE_PERMISSION_CODE = 200;
    MediaRecorder mediaRecorder;
    MediaPlayer player;
//    final MediaPlayer mediaPlayer1 = MediaPlayer.create(this,R.raw.sr);


    TextView txt_accel , txt_prevAcc, txt_currentAccel;
    EditText interval_input;
    EditText beep_input;
    Button saveButton,start_button;

    private  SensorManager mSensorManager;
    private  Sensor mAccelerometer;
    private  Sensor mGyro;
    private  Sensor mMagnetic_Field;
    private  Sensor mGravity;
    private  Sensor mOrientation;
    private  Sensor mLight;
    private  Sensor mPressure;
    private  Sensor mLinearAcc;

    private double accelarationCurrentValue;
    private double accelarationPreviousValue;
    float x,y,z;
    int interval;
    int beep_timer;
    String Acc_x,Acc_y,Acc_z,Acc_curr;
    String Gravity_x,Gravity_y,Gravity_z,Gravity_curr;
    String Gyro_x,Gyro_y,Gyro_z,Gyro_curr;
    String Mag_x,Mag_y,Mag_z,Mag_curr;
    String Orientation_x,Orientation_y,Orientation_z,Orientation_curr;
    String Light_x,Light_curr;
    String Pressure_x,Pressure_curr;
    String LinearAcc_x,LinearAcc_y,LinearAcc_z,LinearAcc_curr;
    String MaxAmplitude;
    String[] Acc = new String[0]  ;
    String[] Gravity = new String[0]  ;
    String[] Gyro = new String[0]  ;
    String[] Mag = new String[0]  ;
    String[] Orientation = new String[0]  ;
    String[] Light = new String[0]  ;
    String[] Pressure = new String[0]  ;
    String[] LinearAcc = new String[0]  ;
    String[] Sound = new String[0];

//    Acc = new String[1];


    private SensorEventListener sensorEventListner = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {


            Acc_x = Float.toString(sensorEvent.values[0]);
            Acc_y = Float.toString(sensorEvent.values[1]);
            Acc_z = Float.toString(sensorEvent.values[2]);
            Acc_curr = Acc_x+" "+Acc_y+" "+Acc_z;

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    private SensorEventListener sensorEventListnerPressure = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            Pressure_x = Float.toString(sensorEvent.values[0]);
            Pressure_curr = Pressure_x;

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    private SensorEventListener sensorEventListnerLinearAcc = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {


            LinearAcc_x = Float.toString(sensorEvent.values[0]);
            LinearAcc_y = Float.toString(sensorEvent.values[1]);
            LinearAcc_z = Float.toString(sensorEvent.values[2]);
            LinearAcc_curr = LinearAcc_x+" "+LinearAcc_y+" "+LinearAcc_z;

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };





    class SayHello extends TimerTask {
        public void run() {



//            System.out.printf("%.2 /n",x);

//                System.out.println("-------========");
//                System.out.printf("%.2f %.2f %.2f",x,y,z);

            Acc = Arrays.copyOf(Acc, Acc.length + 1);
            Acc[Acc.length - 1] = Acc_curr;
//            System.out.println(Arrays.toString(Acc));

            Sound = Arrays.copyOf(Sound, Sound.length + 1);
            Sound[Sound.length - 1] = MaxAmplitude;

            Gyro = Arrays.copyOf(Gyro, Gyro.length + 1);
            Gyro[Gyro.length - 1] = Gyro_curr;
//            System.out.println(Arrays.toString(Gravity));

            Mag = Arrays.copyOf(Mag, Mag.length + 1);
            Mag[Mag.length - 1] = Mag_curr;
            System.out.println(Arrays.toString(Mag));
//            System.out.println(interval_input.getText());

            Gravity = Arrays.copyOf(Gravity, Gravity.length + 1);
            Gravity[Gravity.length - 1] = Gravity_curr;

            Orientation = Arrays.copyOf(Orientation, Orientation.length + 1);
            Orientation[Orientation.length - 1] = Orientation_curr;

            Light = Arrays.copyOf(Light, Light.length + 1);
            Light[Light.length - 1] = Light_curr;

            Pressure = Arrays.copyOf(Pressure, Pressure.length + 1);
            Pressure[Pressure.length - 1] = Pressure_curr;

            LinearAcc = Arrays.copyOf(LinearAcc, LinearAcc.length + 1);
            LinearAcc[LinearAcc.length - 1] = LinearAcc_curr;


            double MaxAmplitude_double = mediaRecorder.getMaxAmplitude();
            System.out.println("amp "+  MaxAmplitude_double );
            MaxAmplitude = Double.toString(MaxAmplitude_double);
        }
    }

//    class SayBeep extends TimerTask {
//        public void run() {
//            System.out.println("hiiiiii");
//
////                mediaPlayer.start();
//        }
//    }



    private SensorEventListener sensorEventListnerG = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            Gyro_x = Float.toString(sensorEvent.values[0]);
            Gyro_y = Float.toString(sensorEvent.values[1]);
            Gyro_z = Float.toString(sensorEvent.values[2]);
            Gyro_curr = Gyro_x+" "+Gyro_y+" "+Gyro_z;


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private SensorEventListener sensorEventListnerGravity = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
//            x = sensorEvent.values[0];
//            y = sensorEvent.values[1];
//            z = sensorEvent.values[2];
//            System.out.printf("%.2f %.2f %.2f\n",x,y,z);
            Gravity_x = Float.toString(sensorEvent.values[0]);
            Gravity_y = Float.toString(sensorEvent.values[1]);
            Gravity_z = Float.toString(sensorEvent.values[2]);
            Gravity_curr = Gravity_x+" "+Gravity_y+" "+Gravity_z;


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private SensorEventListener sensorEventListnerOrientation = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
//            x = sensorEvent.values[0];
//            y = sensorEvent.values[1];
//            z = sensorEvent.values[2];
//            System.out.printf("%.2f %.2f %.2f\n",x,y,z);
            Orientation_x = Float.toString(sensorEvent.values[0]);
            Orientation_y = Float.toString(sensorEvent.values[1]);
            Orientation_z = Float.toString(sensorEvent.values[2]);
            Orientation_curr = Orientation_x+" "+Orientation_y+" "+Orientation_z;


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };


    public boolean isMicPresent()
    {
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
        {
           return true;
        }
        else
        {
            return false;
        }
    }

    public void getMicrophonePermission()
    {
      if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                                    == PackageManager.PERMISSION_DENIED)
      {
          ActivityCompat.requestPermissions(this,new String[]
                  {Manifest.permission.RECORD_AUDIO},MICROPHONE_PERMISSION_CODE);
      }
    }



    private SensorEventListener sensorEventListnerM = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            Mag_x = Float.toString(sensorEvent.values[0]);
            Mag_y = Float.toString(sensorEvent.values[1]);
            Mag_z = Float.toString(sensorEvent.values[2]);
            Mag_curr = Mag_x+" "+Mag_y+" "+Mag_z;

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    private SensorEventListener sensorEventListnerLight = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
//

            Light_x = Float.toString(sensorEvent.values[0]);
            Light_curr = Light_x;

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = MediaPlayer.create(this,R.raw.beep);
        player.setLooping(true);



        System.out.println("Hi");
        if(isMicPresent())
        {
            getMicrophonePermission();
        }


        start_button = findViewById(R.id.Start_button);
        saveButton = findViewById(R.id.save_button);
        interval_input = findViewById(R.id.interval);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createAndSaveFile();

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAndSaveFile();
            }
        });
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (player != null)
                {
                    player.start();
                }
                else
                {
                    System.out.println("empty");
                }
//
                interval_input.getText();
                Acc = new String[0]  ;
                Gyro = new String[0]  ;
                Mag = new String[0]  ;
                Gravity = new String[0];
                Orientation = new String[0]  ;
                Light = new String[0]  ;
                Pressure = new String[0]  ;
                LinearAcc = new String[0]  ;
                Sound = new String[0]  ;

                interval = Integer.parseInt(interval_input.getText().toString());



//                mediaPlayer.start();

                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                executor.scheduleAtFixedRate(new SayHello(), 0, interval, TimeUnit.SECONDS);
//


//                ScheduledExecutorService executor1 = Executors.newScheduledThreadPool(2);
//                executor1.scheduleAtFixedRate(new SayBeep(), 0, beep_timer, TimeUnit.SECONDS);

                System.out.println(interval);


                try {
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setOutputFile(getRecordingFilePath());
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mediaRecorder.prepare();
                    System.out.println("Started Rec");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaRecorder.start();




            }
        });





        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mMagnetic_Field = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mOrientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mLinearAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

//
//        Handler handler = new Handler();
//        handler.postDelayed(new SayHello(), 10000);

//        System.out.println("hi");
//        System.out.println(Acc_curr);

    }



    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListner, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListnerG, mGyro, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListnerM, mMagnetic_Field, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListnerGravity, mGravity, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListnerOrientation, mOrientation, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListnerLight, mLight, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListnerPressure, mPressure, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListnerLinearAcc, mLinearAcc, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListner);
    }

    //////////////////

    private void createAndSaveFile()
    {

        mediaRecorder.stop();
        player.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        System.out.println("Rec Stopped");
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/palin");
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        intent.putExtra(Intent.EXTRA_TITLE,timeStamp+".txt");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                try {
                    Uri uri = data.getData();
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    System.out.println("hell "+Acc);
                    outputStream.write((Arrays.toString(Acc)).getBytes());//.substring(1,Arrays.toString(Acc).length()-2)
                    outputStream.write(10);
                    outputStream.write((Arrays.toString(Gyro)).getBytes());
                    outputStream.write(10);
                    outputStream.write((Arrays.toString(Mag)).getBytes());
                    outputStream.write(10);
                    outputStream.write((Arrays.toString(Gravity)).getBytes());
                    outputStream.write(10);
                    outputStream.write((Arrays.toString(Orientation)).getBytes());
                    outputStream.write(10);
                    outputStream.write((Arrays.toString(Light)).getBytes());
                    outputStream.write(10);
                    outputStream.write((Arrays.toString(Pressure)).getBytes());
                    outputStream.write(10);
                    outputStream.write((Arrays.toString(LinearAcc)).getBytes());
                    outputStream.write(10);
                    outputStream.write((Arrays.toString(Sound)).getBytes());
                    outputStream.close();
                    System.out.println("saved+++++");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("not saved +++++");
            }
            }
        }






//    public double getAmplitude() {
//        if (mRecorder != null)
//            return  mRecorder.getMaxAmplitude();
//        else
//            return 0;
//
//    }
    public String getRecordingFilePath()
    {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory,timeStamp+".wav");
        return file.getPath();
    }



}