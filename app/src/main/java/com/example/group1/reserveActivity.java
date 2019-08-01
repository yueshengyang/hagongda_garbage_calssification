package com.example.group1;

import android.content.Intent;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Date;
import java.util.Locale;

public class reserveActivity extends AppCompatActivity {

    private Button button;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault());
        String sheep=sdf.format(new Date());

        EditText editText=(EditText)findViewById(R.id.time_input);
        editText.setText(sheep);


        button=(Button)findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                showDialog();
//                startActivity(intent);
            }
        });
    }
    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        String name="";
        String time="";
        String address="";
        String phone="";

        EditText editText =null;
        editText=(EditText)findViewById(R.id.name_input);
        name=editText.getText().toString();
        editText=(EditText)findViewById(R.id.time_input);
        time=editText.getText().toString();
        editText=(EditText)findViewById(R.id.address_input);
        address=editText.getText().toString();
        editText=(EditText)findViewById(R.id.phone_input);
        phone=editText.getText().toString();

//        System.out.println("111");
//        System.out.println(name);
//        System.out.println("111");
        final boolean flag=!name.equals("")&&!time.equals("")&&!address.equals("")&&!phone.equals("");
        String out=name+"先生/女士，\n您在扔了么下的服务订单将于"+time+"到达地址"+address+"，\n届时将会通过您的电话"+phone+"与您联系。";

        builder.setTitle("提示：");
        if (flag)
            builder.setMessage(out);
        else {
            builder.setMessage("信息未输入完成");
        }
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (flag){
                            Intent intent = new Intent(reserveActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

}
