package com.example.group1;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class text_inquire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_inquire);
        Button confirm = (Button) findViewById(R.id.confirm);
        final EditText inquire_text = (EditText) findViewById(R.id.inquire_content);
        final TextView inquire_result = (TextView) findViewById(R.id.inquire_result);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inquire = inquire_text.getText().toString();
                try {
                    String[] result = getCategory(inquire);
                    if(result.length==2){
                        inquire_result.setText(result[0]);

                    }else{
                        inquire_result.setText("查询失败！！！");
                    }
                } catch (IOException e) {
                    inquire_result.setText("查询失败！！！");
                    e.printStackTrace();
                }
//                inquire_result.setText("sdadadasdsadadsa");
            }
        });
    }

    public static String[] getCategory(String garbage) throws IOException {
        URL url = new URL("https://quark.sm.cn/api/quark_sug?q=+"+garbage+"+是什么垃圾\n");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine+"\n");
        }
        String line=content.toString();
        String pattern = "\"answer\":\"(\\S+)\",\"description\":\"(\\S+。)\"";
        in.close();
        con.disconnect();
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);
        if (m.find( )) {
            String[] s=new String[2];
            s[0]=m.group(1);
            s[1]=m.group(2);
            return s;
        }
        String[] s=new String[1];
        s[0]="没有匹配";
        return s;
    }

}
