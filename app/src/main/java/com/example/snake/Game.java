package com.example.snake;

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
import android.widget.ImageView;

public class Game extends AppCompatActivity implements GestureDetector.OnGestureListener  {
    boolean isDarkTheme=false;
    boolean isSwipes=true;
    boolean isGyroscope=true;
    boolean isImageType=true;
    boolean isSquareType=false;
    View view;
    Intent mainIntent;
    GameLogic game=new GameLogic();
    GestureDetector gestureDetector;
    SensorManager sensorManager;
    Sensor gyroscopeSensor;
    SensorEventListener gyroscopeEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainIntent=getIntent();
        isDarkTheme=mainIntent.getBooleanExtra(Settings.NAME_OF_EXTRA_DARK_THEME,false);
        isGyroscope=mainIntent.getBooleanExtra(Settings.NAME_OF_EXTRA_GYROSCOPE,true);
        isSwipes=mainIntent.getBooleanExtra(Settings.NAME_OF_EXTRA_SWIPES,true);
        isImageType=mainIntent.getBooleanExtra(Settings.NAME_OF_EXTRA_IMAGE_TYPE,true);
        isSquareType=mainIntent.getBooleanExtra(Settings.NAME_OF_EXTRA_SQUARE_TYPE,false);
        if(isDarkTheme){
            setTheme(R.style.AppThemeDark);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view=new ImageView(getBaseContext());

        gestureDetector=new GestureDetector(this);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        gyroscopeSensor=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gyroscopeEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(isGyroscope){
                    if(sensorEvent.values[2]>3){

                    }else if(sensorEvent.values[2]<-3){

                    }
                    if(sensorEvent.values[1]>2){
                        game.MoveRight();
                    }else if(sensorEvent.values[1]<-3){
                        game.MoveLeft();
                    }
                    if(sensorEvent.values[0]>2){
                        game.MoveDown();
                    }else if(sensorEvent.values[0]<-3){
                        game.MoveUp();
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        game.view=view;
        game.size=40+mainIntent.getIntExtra(Settings.NAME_OF_EXTRA_SIZE,7)*10;
        game.speed=1000/(mainIntent.getIntExtra(Settings.NAME_OF_EXTRA_SPEED,4)+1);
        game.gameHeight = displayMetrics.heightPixels;
        game.gameWide = displayMetrics.widthPixels;
        game.ll=findViewById(R.id.field);
        game.isImageType=isImageType;
        game.isSquareType=isSquareType;
        game.Start();


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

    private void OnSwipeLeft(){
        game.MoveLeft();
    }
    private void OnSwipeRight(){
        game.MoveRight();

    }
    private void OnSwipeUp(){
        game.MoveUp();
    }
    private void OnSwipeDown(){
        game.MoveDown();
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
        if(isSwipes){
            if(Math.abs(deltaX)>Math.abs(deltaY)){
                //right or left
                if(Math.abs(deltaX)>game.MIN_SWIPE_VALUE&&Math.abs(velosityX)>game.MIN_VELOSITY_SWIPE_VALUE){
                    if(deltaX>0){//left
                        OnSwipeLeft();
                    }else{//right
                        OnSwipeRight();
                    }
                    result=true;
                }
            }else{
                //up or down
                if(Math.abs(deltaY)>game.MIN_SWIPE_VALUE&&Math.abs(velosityY)>game.MIN_VELOSITY_SWIPE_VALUE){
                    if(deltaY>0){//up
                        OnSwipeUp();
                    }else{//down
                        OnSwipeDown();
                    }
                    result=true;
                }

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
