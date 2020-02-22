package com.example.snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameLogic extends AppCompatActivity {

    public double MIN_SWIPE_VALUE = 40;
    double MIN_VELOSITY_SWIPE_VALUE=10;
    int innerShift=3;
    int gameWide;
    int gameHeight;
    int size=64;
    int speed=200;//the bigger it the slower snake
    int snakeSize=3;
    int score=0;
    boolean isSquareType=false;
    boolean isImageType=true;
    Paint snakeColor=new Paint();
    Paint appleColor=new Paint();

    boolean isGoing=true;
    boolean hasTurned =false;
    LinearLayout ll;
    LinearLayout.LayoutParams layoutParams;

    Handler timer=new Handler();
    Runnable timerRunnable=new Runnable() {
        @Override
        public void run() {
            Move();
            CheckApple();
            CheckSnake();
            if(isGoing){
                if(isImageType){
                    DrawImageSnake();
                }
                if(isSquareType){
                    DrawSquareSnake();
                }
                hasTurned =false;
                timer.postDelayed(this,speed);
            }
            else{
                Toast.makeText(ll.getContext(),"You Lost",Toast.LENGTH_LONG).show();
            }
        }
    };


    int whereToMove=1;//0-nowhere,1-up,2-right,3-down,4-left
    Point apple=new Point();
    Point[] position=new Point[1000];
    View view;
    ImageView[] image=new ImageView[1000];
    ImageView appleImage;
    Random rnd=new Random();

    protected void Start() {
        layoutParams=new LinearLayout.LayoutParams(gameWide,gameHeight);
        appleColor.setColor(Color.RED);
        appleColor.setStyle(Paint.Style.FILL);
        snakeColor.setColor(Color.GREEN);
        snakeColor.setStyle(Paint.Style.FILL);
        appleImage=new ImageView(view.getContext());
        appleImage.setVisibility(View.VISIBLE);
        ll.addView(appleImage);
        appleImage.setLayoutParams(layoutParams);
        layoutParams.height=size;
        layoutParams.width=size;
        for(int i=0;i<snakeSize;i++){
            position[i]=new Point();
            image[i]=new ImageView(view.getContext());
            image[i].setVisibility(View.VISIBLE);
            ll.addView(image[i]);
            image[i].setLayoutParams(layoutParams);
        }
        position[0].y=gameHeight/2/size;
        position[0].x=gameWide/2/size;
        for(int i=1;i<snakeSize;i++){
            position[i].x=position[0].x;
            position[i].y=position[0].y;
        }
        CreateApple();
        timer.postDelayed(timerRunnable,0);
    }
    private void CheckApple(){
        if(position[0].x==apple.x&&position[0].y==apple.y){
            snakeSize++;
            score++;
            position[snakeSize-1]=new Point();
            image[snakeSize-1]=new ImageView(view.getContext());
            position[snakeSize-1].x=position[snakeSize-2].x;
            position[snakeSize-1].y=position[snakeSize-2].y;
            image[snakeSize-1].setVisibility(View.VISIBLE);
            image[snakeSize-1].setLayoutParams(layoutParams);
            ll.addView(image[snakeSize-1]);
            CreateApple();
        }
    }
    private void CreateApple(){
        apple.x=rnd.nextInt(gameWide/size);
        apple.y=rnd.nextInt(gameHeight/size);
        for(int i=0;i<snakeSize;i++){
            if(position[i].x==apple.x&&position[i].y==apple.y){
                CreateApple();
                break;
            }
        }
    }
    private void Move(){
        switch (whereToMove){
            case 1:
                for(int i=snakeSize-1;i>0;i--){
                    position[i].x=position[i-1].x;
                    position[i].y=position[i-1].y;
                }
                position[0].y-=1;
                if(position[0].y<0){
                    position[0].y=gameHeight/size;
                }
                break;
            case 2:
                for(int i=snakeSize-1;i>0;i--){
                    position[i].x=position[i-1].x;
                    position[i].y=position[i-1].y;
                }
                position[0].x+=1;
                if(position[0].x>gameWide/size){
                    position[0].x=0;
                }
                break;
            case 3:
                for(int i=snakeSize-1;i>0;i--){
                    position[i].x=position[i-1].x;
                    position[i].y=position[i-1].y;
                }
                position[0].y+=1;
                if(position[0].y>gameHeight/size){
                    position[0].y=0;
                }
                break;
            case 4:
                for(int i=snakeSize-1;i>0;i--){
                    position[i].x=position[i-1].x;
                    position[i].y=position[i-1].y;
                }
                position[0].x-=1;
                if(position[0].x<0){
                    position[0].x=gameWide/size;
                }
                break;
        }
    }
    private void DrawSquareSnake(){
        Bitmap field=Bitmap.createBitmap(gameWide,gameHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(field);
        for(int i=0;i<snakeSize;i++){
            canvas.drawRect(position[i].x*size+innerShift,position[i].y*size+innerShift,position[i].x*size+size-innerShift,position[i].y*size+size-innerShift,snakeColor);
        }
        canvas.drawRect(apple.x*size+innerShift,apple.y*size+innerShift,apple.x*size+size-innerShift,apple.y*size+size-innerShift,appleColor);
        ll.setBackground(new BitmapDrawable(field));
    }
    private void DrawImageSnake(){
        image[0].setX(position[0].x*size);
        image[0].setY(position[0].y*size);
        image[0].setImageDrawable(view.getResources().getDrawable(R.drawable.snake_head));
        image[0].requestLayout();
        image[0].setRotation((whereToMove-1)*90);
        for(int i=1;i<snakeSize-1;i++){
            image[i].setX(position[i].x*size);
            image[i].setY(position[i].y*size);
            image[i].setImageDrawable(view.getResources().getDrawable(R.drawable.snake_turn2));
            image[i].requestLayout();
            if((position[i-1].y>position[i].y||(position[i-1].y==0&&position[i].y==gameHeight/size))&&
                    ((position[i].y!=0&&position[i-1].y!=gameHeight/size)||(position[i].y!=0&&position[i-1].y==gameHeight/size)||(position[i].y==0&&position[i-1].y!=gameHeight/size))&&
                    (position[i+1].x<position[i].x||(position[i].x==0&&position[i+1].x==gameWide/size))&&
                    ((position[i+1].x!=0&&position[i].x!=gameWide/size)||(position[i+1].x==0&&position[i].x!=gameWide/size)||(position[i+1].x!=0&&position[i].x==gameWide/size)))
                image[i].setRotation(270);
            else if((position[i-1].y>position[i].y||(position[i-1].y==0&&position[i].y==gameHeight/size))&&
                    ((position[i].y!=0&&position[i-1].y!=gameHeight/size)||(position[i].y==0&&position[i-1].y!=gameHeight/size)||(position[i].y!=0&&position[i-1].y==gameHeight/size))&&
                    (position[i+1].x>position[i].x||(position[i+1].x==0&&position[i].x==gameWide/size))&&
                    ((position[i].x!=0&&position[i+1].x!=gameWide/size)||(position[i].x==0&&position[i+1].x!=gameWide/size)||(position[i].x!=0&&position[i+1].x==gameWide/size)))
                image[i].setRotation(180);
            else if((position[i-1].y<position[i].y||(position[i].y==0&&position[i-1].y==gameHeight/size))&&
                    ((position[i-1].y!=0&&position[i].y!=gameHeight/size)||(position[i-1].y==0&&position[i].y!=gameHeight/size)||(position[i-1].y!=0&&position[i].y==gameHeight/size))&&
                    (position[i+1].x<position[i].x||(position[i].x==0&&position[i+1].x==gameWide/size))&&
                    ((position[i+1].x!=0&&position[i].x!=gameWide/size)||(position[i+1].x==0&&position[i].x!=gameWide/size)||(position[i+1].x!=0&&position[i].x==gameWide/size))){
                image[i].setRotation(0);
            }else if((position[i-1].y<position[i].y||(position[i].y==0&&position[i-1].y==gameHeight/size))&&
                    ((position[i-1].y!=0&&position[i].y!=gameHeight/size)||(position[i-1].y==0&&position[i].y!=gameHeight/size)||(position[i-1].y!=0&&position[i].y==gameHeight/size))&&
                    (position[i+1].x>position[i].x||(position[i+1].x==0&&position[i].x==gameWide/size))&&
                    ((position[i].x!=0&&position[i+1].x!=gameWide/size)||(position[i].x==0&&position[i+1].x!=gameWide/size)||(position[i].x!=0&&position[i+1].x==gameWide/size)))
                image[i].setRotation(90);
            else if((position[i-1].x>position[i].x||(position[i-1].x==0&&position[i].x==gameWide/size))&&
                    ((position[i].x!=0&&position[i-1].x!=gameWide/size)||(position[i].x==0&&position[i-1].x!=gameWide/size)||(position[i].x!=0&&position[i-1].x==gameWide/size))&&
                    (position[i+1].y>position[i].y||(position[i+1].y==0&&position[i].y==gameHeight/size))&&
                    ((position[i].y!=0&&position[i+1].y!=gameHeight/size)||(position[i].y==0&&position[i+1].y!=gameHeight/size)||(position[i].y!=0&&position[i+1].y==gameHeight/size)))
                image[i].setRotation(180);
            else if((position[i-1].x>position[i].x||(position[i-1].x==0&&position[i].x==gameWide/size))&&
                    ((position[i].x!=0&&position[i-1].x!=gameWide/size)||(position[i].x==0&&position[i-1].x!=gameWide/size)||(position[i].x!=0&&position[i-1].x==gameWide/size))&&
                    (position[i+1].y<position[i].y||(position[i].y==0&&position[i+1].y==gameHeight/size))&&
                    ((position[i+1].y!=0&&position[i].y!=gameHeight/size)||(position[i+1].y==0&&position[i].y!=gameHeight/size)||(position[i+1].y!=0&&position[i].y==gameHeight/size)))
                image[i].setRotation(90);
            else if((position[i-1].x<position[i].x||(position[i].x==0&&position[i-1].x==gameWide/size))&&
                    ((position[i-1].x!=0&&position[i].x!=gameWide/size)||(position[i-1].x==0&&position[i].x!=gameWide/size)||(position[i-1].x!=0&&position[i].x==gameWide/size))&&
                    (position[i+1].y>position[i].y||(position[i+1].y==0&&position[i].y==gameHeight/size))&&
                    ((position[i].y!=0&&position[i+1].y!=gameHeight/size)||(position[i].y==0&&position[i+1].y!=gameHeight/size)||(position[i].y!=0&&position[i+1].y==gameHeight/size))) {
                image[i].setRotation(270);
            }else if((position[i-1].x<position[i].x||(position[i].x==0&&position[i-1].x==gameWide/size))&&
                    ((position[i-1].x!=0&&position[i].x!=gameWide/size)||(position[i-1].x==0&&position[i].x!=gameWide/size)||(position[i-1].x!=0&&position[i].x==gameWide/size))&&
                    (position[i+1].y<position[i].y||(position[i].y==0&&position[i+1].y==gameHeight/size))&&
                    ((position[i+1].y!=0&&position[i].y!=gameHeight/size)||(position[i+1].y==0&&position[i].y!=gameHeight/size)||(position[i+1].y!=0&&position[i].y==gameHeight/size))){
                image[i].setRotation(0);
            }else{
                image[i].setImageDrawable(view.getResources().getDrawable(R.drawable.snake_straight));
                if(position[i-1].x==position[i].x&&position[i+1].x==position[i].x){
                    image[i].setRotation(0);
                }else if(position[i-1].y==position[i].y&&position[i+1].y==position[i].y){
                    image[i].setRotation(90);
                }
            }
        }
        image[snakeSize-1].setX(position[snakeSize-1].x*size);
        image[snakeSize-1].setY(position[snakeSize-1].y*size);
        image[snakeSize-1].setImageDrawable(view.getResources().getDrawable(R.drawable.snake_tail));
        image[snakeSize-1].requestLayout();
        if((position[snakeSize-2].x>position[snakeSize-1].x||(position[snakeSize-2].x==0&&position[snakeSize-1].x==gameWide/size))&&
                ((position[snakeSize-1].x!=0&&position[snakeSize-2].x!=gameWide/size)||(position[snakeSize-1].x==0&&position[snakeSize-2].x!=gameWide/size)||(position[snakeSize-1].x!=0&&position[snakeSize-2].x==gameWide/size)))
            image[snakeSize-1].setRotation(90);
        else if((position[snakeSize-2].x<position[snakeSize-1].x||(position[snakeSize-2].x==gameWide/size&&position[snakeSize-1].x==0))&&
                ((position[snakeSize-1].x!=gameWide/size&&position[snakeSize-2].x!=0)||(position[snakeSize-1].x==gameWide/size&&position[snakeSize-2].x!=0)||(position[snakeSize-1].x!=gameWide/size&&position[snakeSize-2].x==0)))
            image[snakeSize-1].setRotation(270);
        else if((position[snakeSize-2].y>position[snakeSize-1].y||(position[snakeSize-2].y==0&&position[snakeSize-1].y==gameHeight/size))&&
                ((position[snakeSize-1].y!=0&&position[snakeSize-2].y!=gameHeight/size)||(position[snakeSize-1].y==0&&position[snakeSize-2].y!=gameHeight/size)||(position[snakeSize-1].y!=0&&position[snakeSize-2].y==gameHeight/size)))
            image[snakeSize-1].setRotation(180);
        else  if((position[snakeSize-2].y<position[snakeSize-1].y||(position[snakeSize-2].y==gameHeight/size&&position[snakeSize-1].y==0))&&
                ((position[snakeSize-1].y!=gameHeight/size&&position[snakeSize-2].y!=0)||(position[snakeSize-1].y==gameHeight/size&&position[snakeSize-2].y!=0)||(position[snakeSize-1].y!=gameHeight/size&&position[snakeSize-2].y==0)))
            image[snakeSize-1].setRotation(0);

        appleImage.setX(apple.x*size);
        appleImage.setY(apple.y*size);
        appleImage.setImageDrawable(view.getResources().getDrawable(R.drawable.apple));
        appleImage.requestLayout();

    }
    private void CheckSnake(){
        for(int i=1;i<snakeSize;i++){
            if(position[i].x==position[0].x&&position[i].y==position[0].y){
                isGoing=false;
                break;
            }
        }
    }
    protected void MoveRight(){
        if(whereToMove!=4&&!hasTurned){
            whereToMove=2;
            hasTurned =true;
        }
    }
    protected void MoveLeft(){
        if(whereToMove!=2&&!hasTurned){
            whereToMove=4;
            hasTurned =true;
        }
    }
    protected void MoveUp(){
        if(whereToMove!=3&&!hasTurned){
            whereToMove=1;
            hasTurned =true;
        }
    }
    protected void MoveDown(){
        if(whereToMove!=1&&!hasTurned){
            whereToMove=3;
            hasTurned =true;
        }
    }

}
