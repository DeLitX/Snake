package com.example.snake;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    Intent mainIntent;
    public static final int settingsRequestCode=1;
    int snakeSpeed=4;
    int snakeSize=7;
    boolean isDarkTheme=false;
    boolean isGyroscope=true;
    boolean isSwipes=true;
    boolean isSquareType=false;
    boolean isImageType=true;
    SensorManager sensorManager;
    Sensor gyroscopeSensor;
    SensorEventListener gyroscopeEventListener;
    GestureDetector gestureDetector;
    GameLogic game=new GameLogic();

    Button start,settings;

    int[] shape=new int[]{0,0,0,0,0};

    int timerScore=0;
    int height,width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainIntent=getIntent();
        isDarkTheme=mainIntent.getBooleanExtra(Settings.NAME_OF_EXTRA_DARK_THEME,false);
        if(isDarkTheme){
            setTheme(R.style.AppThemeDark);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        gyroscopeSensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        snakeSpeed=mainIntent.getIntExtra(Settings.NAME_OF_EXTRA_SPEED,4);
        snakeSize=mainIntent.getIntExtra(Settings.NAME_OF_EXTRA_SIZE,7);
        isGyroscope=mainIntent.getBooleanExtra(Settings.NAME_OF_EXTRA_GYROSCOPE,true);
        isSwipes=mainIntent.getBooleanExtra(Settings.NAME_OF_EXTRA_SWIPES,true);
        isImageType=mainIntent.getBooleanExtra(Settings.NAME_OF_EXTRA_IMAGE_TYPE,true);
        isSquareType=mainIntent.getBooleanExtra(Settings.NAME_OF_EXTRA_SQUARE_TYPE,false);

        gestureDetector=new GestureDetector(this);

        addActionListener();
        gyroscopeEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==settingsRequestCode){
            if(resultCode==RESULT_OK){
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                intent.putExtra(Settings.NAME_OF_EXTRA_DARK_THEME,data.getBooleanExtra(Settings.NAME_OF_EXTRA_DARK_THEME,false));
                intent.putExtra(Settings.NAME_OF_EXTRA_SPEED,data.getIntExtra(Settings.NAME_OF_EXTRA_SPEED,4));
                intent.putExtra(Settings.NAME_OF_EXTRA_SWIPES,data.getBooleanExtra(Settings.NAME_OF_EXTRA_SWIPES,true));
                intent.putExtra(Settings.NAME_OF_EXTRA_GYROSCOPE,data.getBooleanExtra(Settings.NAME_OF_EXTRA_GYROSCOPE,true));
                intent.putExtra(Settings.NAME_OF_EXTRA_IMAGE_TYPE,data.getBooleanExtra(Settings.NAME_OF_EXTRA_IMAGE_TYPE,true));
                intent.putExtra(Settings.NAME_OF_EXTRA_SQUARE_TYPE,data.getBooleanExtra(Settings.NAME_OF_EXTRA_SQUARE_TYPE,false));
                intent.putExtra(Settings.NAME_OF_EXTRA_SIZE,data.getIntExtra(Settings.NAME_OF_EXTRA_SIZE,7));
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeEventListener,gyroscopeSensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(gyroscopeEventListener);
    }

    private void addActionListener(){
        start=(Button)findViewById(R.id.start);
        settings=(Button)findViewById(R.id.settings);
        start.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MainActivity.this,Game.class);
                        intent.putExtra(Settings.NAME_OF_EXTRA_DARK_THEME,isDarkTheme);
                        intent.putExtra(Settings.NAME_OF_EXTRA_SPEED,snakeSpeed);
                        intent.putExtra(Settings.NAME_OF_EXTRA_SWIPES,isSwipes);
                        intent.putExtra(Settings.NAME_OF_EXTRA_GYROSCOPE,isGyroscope);
                        intent.putExtra(Settings.NAME_OF_EXTRA_SIZE,snakeSize);
                        intent.putExtra(Settings.NAME_OF_EXTRA_SQUARE_TYPE,isSquareType);
                        intent.putExtra(Settings.NAME_OF_EXTRA_IMAGE_TYPE,isImageType);
                        startActivity(intent);
                    }
                }
        );
        settings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MainActivity.this,Settings.class);
                        intent.putExtra(Settings.NAME_OF_EXTRA_DARK_THEME,isDarkTheme);
                        intent.putExtra(Settings.NAME_OF_EXTRA_SPEED,snakeSpeed);
                        intent.putExtra(Settings.NAME_OF_EXTRA_SWIPES,isSwipes);
                        intent.putExtra(Settings.NAME_OF_EXTRA_GYROSCOPE,isGyroscope);
                        intent.putExtra(Settings.NAME_OF_EXTRA_SIZE,snakeSize);
                        intent.putExtra(Settings.NAME_OF_EXTRA_SQUARE_TYPE,isSquareType);
                        intent.putExtra(Settings.NAME_OF_EXTRA_IMAGE_TYPE,isImageType);
                        startActivityForResult(intent,settingsRequestCode);
                    }
                }
        );

    }
    private void OnSwipeLeft(){
    }
    private void OnSwipeRight(){
    }
    private void OnSwipeUp(){
    }
    private void OnSwipeDown(){
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float velosityX, float velosityY) {
        boolean result=false;
        double deltaX=downEvent.getX()-moveEvent.getX();
        double deltaY=downEvent.getY()-moveEvent.getY();

        if(Math.abs(deltaX)>Math.abs(deltaY)){
            //right or left
            if(Math.abs(deltaX)>game.MIN_SWIPE_VALUE&&velosityX>game.MIN_VELOSITY_SWIPE_VALUE){
                if(deltaX>0){//left
                    OnSwipeLeft();
                }else{//right
                    OnSwipeRight();
                }
                result=true;
            }
        }else{
            //up or down
            if(Math.abs(deltaY)>game.MIN_SWIPE_VALUE&&velosityY>game.MIN_VELOSITY_SWIPE_VALUE){
                if(deltaY>0){//up
                    OnSwipeUp();
                }else{//down
                    OnSwipeDown();
                }
                result=true;
            }

        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
