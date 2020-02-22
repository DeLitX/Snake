package com.example.snake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

public class Settings extends AppCompatActivity {
    public static final String NAME_OF_EXTRA_SPEED="speed";
    public static final String NAME_OF_EXTRA_DARK_THEME="darkTheme";
    public static final String NAME_OF_EXTRA_GYROSCOPE="gyroscope";
    public static final String NAME_OF_EXTRA_SWIPES="swipes";
    public static final String NAME_OF_EXTRA_SIZE="size";
    public static final String NAME_OF_EXTRA_SQUARE_TYPE="squareType";
    public static final String NAME_OF_EXTRA_IMAGE_TYPE="imageType";
    boolean isDarkTheme=false;
    boolean isGyroscope=true;
    boolean isSwipes=true;
    boolean isSquareType=false;
    boolean isImageType=true;
    int snakeSpeed,snakeSize;
    Switch darkTheme,gyroscope,swipes;
    SeekBar speed,size;
    Intent mainIntent;
    Button squareType,imageType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainIntent=getIntent();
        isDarkTheme=mainIntent.getBooleanExtra(NAME_OF_EXTRA_DARK_THEME,false);
        isGyroscope=mainIntent.getBooleanExtra(NAME_OF_EXTRA_GYROSCOPE,true);
        isSwipes=mainIntent.getBooleanExtra(NAME_OF_EXTRA_SWIPES,true);
        isImageType=mainIntent.getBooleanExtra(NAME_OF_EXTRA_IMAGE_TYPE,true);
        isSquareType=mainIntent.getBooleanExtra(NAME_OF_EXTRA_SQUARE_TYPE,false);
        if(isDarkTheme){
            setTheme(R.style.AppThemeDark);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        snakeSpeed=mainIntent.getIntExtra(NAME_OF_EXTRA_SPEED,4);
        snakeSize=mainIntent.getIntExtra(NAME_OF_EXTRA_SIZE,7);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addActionListener();
    }
    private void addActionListener(){
        darkTheme=findViewById(R.id.darkTheme);
        darkTheme.setChecked(isDarkTheme);
        gyroscope=findViewById(R.id.gyroscope);
        gyroscope.setChecked(isGyroscope);
        swipes=findViewById(R.id.swipes);
        swipes.setChecked(isSwipes);
        speed=findViewById(R.id.seekBarSpeed);
        speed.setProgress(snakeSpeed);
        size=findViewById(R.id.seekBarSize);
        size.setProgress(snakeSize);
        squareType=findViewById(R.id.typeSquare);
        imageType=findViewById(R.id.typeImage);

        darkTheme.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendData();

                        Intent newIntent=new Intent(Settings.this,Settings.class);
                        newIntent.putExtra(NAME_OF_EXTRA_DARK_THEME,darkTheme.isChecked());
                        newIntent.putExtra(NAME_OF_EXTRA_SPEED,snakeSpeed);
                        newIntent.putExtra(NAME_OF_EXTRA_GYROSCOPE,gyroscope.isChecked());
                        newIntent.putExtra(NAME_OF_EXTRA_SWIPES,swipes.isChecked());
                        newIntent.putExtra(NAME_OF_EXTRA_SIZE,size.getProgress());
                        newIntent.putExtra(NAME_OF_EXTRA_SQUARE_TYPE,isSquareType);
                        newIntent.putExtra(NAME_OF_EXTRA_IMAGE_TYPE,isImageType);
                        startActivity(newIntent);
                        finish();
                    }
                }
        );
        speed.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        SendData();
                        snakeSpeed=speed.getProgress();
                    }
                }
        );
        size.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        SendData();
                        snakeSize=size.getProgress();
                    }
                }
        );
        gyroscope.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendData();
                    }
                }
        );
        swipes.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SendData();
                    }
                }
        );
        squareType.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isImageType=false;
                        isSquareType=true;
                        SendData();
                    }
                }
        );
        imageType.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isImageType=true;
                        isSquareType=false;
                        SendData();
                    }
                }
        );
    }
    private void SendData(){
        Intent resultIntent=new Intent(Settings.this,MainActivity.class);
        resultIntent.putExtra(NAME_OF_EXTRA_DARK_THEME,darkTheme.isChecked());
        resultIntent.putExtra(NAME_OF_EXTRA_SPEED,speed.getProgress());
        resultIntent.putExtra(NAME_OF_EXTRA_GYROSCOPE,gyroscope.isChecked());
        resultIntent.putExtra(NAME_OF_EXTRA_SWIPES,swipes.isChecked());
        resultIntent.putExtra(NAME_OF_EXTRA_SIZE,size.getProgress());
        resultIntent.putExtra(NAME_OF_EXTRA_SQUARE_TYPE,isSquareType);
        resultIntent.putExtra(NAME_OF_EXTRA_IMAGE_TYPE,isImageType);
        setResult(RESULT_OK,resultIntent);
    }
}
