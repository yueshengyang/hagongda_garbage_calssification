package com.example.group1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.*;

public class image extends AppCompatActivity implements SurfaceHolder.Callback
        , View.OnClickListener, Camera.PictureCallback{

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    private static final String TAG="CameraActivity";

    private final int CAMERA_FRONT=1;//前置摄像头
    private final int CAMERA_BEHIND=0;//后置摄像头
    private int CAMERA_NOW=CAMERA_BEHIND;//默认打开后置摄像头
    private Camera mCamera;
    String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";
    String accessToken = "24.0c62d0b89f6ba24b69b424d6b36398d4.2592000.1567232723.282335-16930388";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Log.e("xiaoanan", "dadingding");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initViews();
    }

    private void initViews(){//初始化控件
        mSurfaceView=findViewById(R.id.surfaceView);
        mSurfaceHolder=mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);//添加回调接口，这一句很重要，一定要添加

        mSurfaceView.setClickable(true);
        mSurfaceView.setOnClickListener(this);
    }


    public Camera getCamera(){//初始化摄像头
        Camera camera;
        try {
            ActivityCompat.requestPermissions(image.this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            camera=Camera.open(CAMERA_NOW);//默认打开后置摄像头
            Log.e("getCamera method inner", camera.toString());
            return camera;
        }catch (Exception e){
            Log.i(TAG,"open camera failed");
            e.printStackTrace();
        }
        return null;
    }

    public void startPreview(Camera camera,SurfaceHolder surfaceHolder){
        if(camera==null){
            mCamera=getCamera();
            Log.e("get camera method", mCamera.toString());
        }else{
            Log.e("out camera", "out's camera");
            mCamera=camera;
        }

        if(surfaceHolder==null){
            surfaceHolder=mSurfaceView.getHolder();
        }

        try {
            mCamera.setDisplayOrientation(90);//安卓默认是横屏，旋转转为90度，转为竖屏
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void releaseCamera(){
        mCamera.stopPreview();
        if(mCamera==null){
            return;
        }
        mCamera.release();
        mCamera=null;
        mSurfaceHolder=null;
    }

    @Override
    protected void onDestroy() {
        //releaseCamera();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        startPreview(mCamera, mSurfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        startPreview(mCamera, mSurfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        releaseCamera();
    }

    @Override
    public void onClick(View view) {
        mCamera.takePicture(null, null, this);
        //  Intent intent = new Intent(image.this, MainActivity.class);
        //  startActivity(intent);
        onDestroy();
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

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        //ContentResolver resolver = getContentResolver();
        // Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        // Log.e("picture", "save is ok?");
        // MediaStore.Images.Media.insertImage(resolver, bitmap, "t", "des");
        //拍照后重新开始预览
        // camera.startPreview();


        try {
            //get token
            // String requsetURL = url.replaceAll("APIKEY",APIKEY).replaceAll("SECRETKEY",SECRETKEY);


//            // 本地文件路径
//            //String filePath = "[本地文件路径]";
//            //byte[] imgData = FileUtil.readFileByBytes(filePath);
            Log.d("jiaqiang","1");
            String imgStr = Base64Util.encode(bytes);
            Log.d("jiaqiang","2");
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            Log.d("jiaqiang","3");
            String param = "image=" + imgParam;
            Log.d("jiaqiang","4");
//            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            //String accessToken = "[调用鉴权接口获取的token]";//密钥
            String result = HttpUtil.post(url, accessToken, param);
            Log.d("jiaqiang","5");
//            // System.out.println(result);
            Log.d("jiaqiang",result);

            // "keyword": "摄像机"
            Pattern keywordPattern = Pattern.compile("\"keyword\": \"(\\S+)\"");
            Matcher matcher = keywordPattern.matcher(result);
            if (matcher.find()){
                String inquire = matcher.group(1);
                String[] inquire_result = getCategory(inquire);
                if(inquire_result.length==2){

                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setMessage("垃圾为："+inquire+"\n识别结果为："+inquire_result[0]);
                    builder.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(image.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setMessage("识别失败！");
                    builder.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(image.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                Log.d("jiaqiang",matcher.group(1));
            }else {
                Log.d("jiaqiang","Missing");
            }

        } catch (Exception e) {
            Log.d("jiaqiang","nagenanren");
            e.printStackTrace();
        }

    }

    public Bitmap getPicFromBytes(byte[] bytes,
                                  BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }
}
