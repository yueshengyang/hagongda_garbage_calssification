package com.example.group1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.os.Vibrator;

public class inquireActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton B1;
    private ImageButton button_text;
    private ImageButton B3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquire);

        B1 = (ImageButton)findViewById(R.id.imageButton10);
        B1.setOnClickListener(MyListener);

        button_text = (ImageButton)findViewById(R.id.text_inquire);
        button_text.setOnClickListener(MyListener);

        B3 = (ImageButton)findViewById(R.id.imageButton8);
        B3.setOnClickListener(MyListener);

    }

    View.OnClickListener MyListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Vibrator vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(30);
            // Intent intent = new Intent(MainActivity.this,image.class);
            switch (view.getId()){
                case R.id.imageButton8:
                    Log.d("tag","video");
                    break;
                case R.id.text_inquire:
//                    Log.d("tag","word");
                    Intent intent = new Intent(inquireActivity.this,text_inquire.class);
                    startActivity(intent);
                    break;
                case R.id.imageButton10:
                    Log.d("tag","image");
                    startActivity(new Intent(inquireActivity.this,image.class));
                    //onCreate(savedInstanceState);
                    //MainActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    public void onClick(View v){

    }
}
