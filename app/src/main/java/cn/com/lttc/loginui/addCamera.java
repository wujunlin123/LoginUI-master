package cn.com.lttc.loginui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class addCamera extends AppCompatActivity implements View.OnClickListener {

    private Button call_Police;
    private  String imageUrl = "http://news.sciencenet.cn/upload/news/images/2011/3/20113301123128272.jpg";
    //手机ubuntu    ip
    // private  String path = "http://192.168.43.39:80/?action=stream";
    //居住地wifi的ubuntu ip
    private  String path = "http://192.168.199.224:80/?action=stream";

    private Button button,Imagebutton;
    private Button SaveImagebutton,SaveVideoButton;
    private ImageView img;
    private Bitmap bmg;
    private static final String SD_PATH = "1/storage/emulated/0/potato/";
    private static final String IN_PATH = "/dskqxt/pic/";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


//    private String path = "http://192.168.126.136:8080";


    //在消息队列中实现对控件的更改
//      private Handler handle = new Handler() {
//          public void handleMessage(Message msg) {
//              switch (msg.what) {
//                  case 0:
//                      Bitmap bmp=(Bitmap)msg.obj;
//                      img.setImageBitmap(bmp);
//                      break;
//              }
//          };
//      };
    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            //更新UI
            ImageView imageView = (ImageView) findViewById(R.id.img);
            imageView.setImageBitmap((Bitmap) msg.obj);
        };
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_camera);
        call_Police = (Button) findViewById(R.id.callPolice);
        call_Police.setOnClickListener(this);
        Imagebutton = (Button) findViewById(R.id.Imagebutton);
        img = (ImageView) findViewById(R.id.img);
        Imagebutton.setOnClickListener(this);
        SaveImagebutton = (Button) findViewById(R.id.SaveImagebutton);
        SaveImagebutton.setOnClickListener(this);

        SaveVideoButton = (Button) findViewById(R.id.SaveVideoButton);
        SaveVideoButton.setOnClickListener(this);



            try {
                //检测是否有写的权限
                int permission = ActivityCompat.checkSelfPermission(this,
                        "android.permission.WRITE_EXTERNAL_STORAGE");
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // 没有写的权限，去申请写的权限，会弹出对话框
                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }





    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.callPolice:
                callPolice();
                break;
            case R.id.Imagebutton:
//            getImage();
//                break;
                getImageThread();
                break;
            case R.id.SaveImagebutton:
               saveImage();
              // saveImages();

//                Object context = null;
//                saveBitmap((Context) context);
                break;
            case R.id.SaveVideoButton:
                try {
                    saveVideo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void saveImages() {
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED))
            // 判断是否可以对SDcard进行操作
         {
             // 获取SDCard指定目录下
             String  sdCardDir = Environment.getExternalStorageDirectory()+ "/CoolImage/";
             File dirFile  = new File(sdCardDir);
             //目录转化成文件夹
             		 if (!dirFile .exists()) {
             		     //如果不存在，那就建立这个文件夹
                         				dirFile .mkdirs();
             		 }
             		 //文件夹有啦，就可以保存图片啦
             //
             File file = new File(sdCardDir, System.currentTimeMillis()+".jpg");
             		 // 在SDcard的目录下创建图片文,以当前时间为其命名
             			try {
                            FileOutputStream  out = new FileOutputStream(file);
             			    bmg.compress(Bitmap.CompressFormat.JPEG, 90, out);
             			    System.out.println("_________保存到____sd______指定目录文件夹下____________________"+sdCardDir+System.currentTimeMillis());
                            try {
                                out.flush();
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

             			} catch (FileNotFoundException e) {
             			    e.printStackTrace();
             			}
             			Toast.makeText(addCamera.this,"保存已经至"+Environment.getExternalStorageDirectory()+"/CoolImage/"+"目录文件夹下", Toast.LENGTH_SHORT).show();			 }

    }

    private void saveVideo() throws IOException {
        Log.i("456","saveVideo12464894156");

    }
    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    private void saveImage() {
        Log.i("123","saveImage0000000");
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"photo"+ File.separator;
//potato文件夹是不存在的所以必须提前创建
        Log.i("dirPath","1"+dir);
        Log.i("bmg","imageMessage-----"+bmg);

        Toast.makeText(addCamera.this,"截图已保存在"+dir,Toast.LENGTH_SHORT).show();
        /**
         * 保存bitmap到本地
         *
         * @param context
         * @param mBitmap
         * @return
         */


        File folder = new File(dir);
        Log.i("123","folder exist?---"+folder.exists());
        if(!folder.exists()){
            folder.mkdirs();
        }else {
            Log.i("123456","true");

        }
        Log.i("123456","folder build success? -----"+folder.mkdir());

        String imaPath = dir + System.currentTimeMillis() + ".jpg";
        File file = new File(imaPath);
        Log.i("123456","message"+file);


        if(!file.exists()){
            try {
                file.createNewFile();//重点在这里

                Log.i("312313","file build success?----"+file.createNewFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                bmg.compress(Bitmap.CompressFormat.JPEG, 100, out);

                out.flush();
                out.close();
                Toast.makeText(addCamera.this,"截图已保存在"+dir,Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * 保存bitmap到本地
     *
     * @param
     * @param
     * @return
     */
//    public String saveBitmap(Context context) {
//
//        String savePath;
//        File filePic;
//        Log.i("123","saveImage0000000");
//        Log.i("123","SD_PATH"+SD_PATH);
//        Log.i("321","IN_PATH"+IN_PATH);
//
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {
//            savePath = SD_PATH;
//        } else {
//            savePath = context.getApplicationContext().getFilesDir()
//                    .getAbsolutePath()
//                    + IN_PATH;
//        }
//        try {
//            filePic = new File(savePath + generateFileName() + ".jpg");
//            if (!filePic.exists()) {
//                filePic.getParentFile().mkdirs();
//                filePic.createNewFile();
//                Toast.makeText(addCamera.this,"截图已保存在"+SD_PATH,Toast.LENGTH_SHORT).show();
//            }
//            FileOutputStream fos = new FileOutputStream(filePic);
//            bmg.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//            fos.close();
//            Log.i("123","SD_PATH"+SD_PATH);
//            Log.i("321","IN_PATH"+IN_PATH);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }
//
//        return filePic.getAbsolutePath();
//    }

    private void getImageThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while(true){
                    getImage();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }                }).start();

    }


    private void getImage() {

        //开启一个线程
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //1: 确定网址

                try {
                    //2:把网址封装为一个URL对象
                    URL url = new URL(path);

                    //3:获取客户端和服务器的连接对象，此时还没有建立连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    //4:初始化连接对象
                    conn.setRequestMethod("GET");
                    //设置连接超时
                    // conn.setConnectTimeout(5000);
                    //设置读取超时
                    // conn.setReadTimeout(5000);
                    //5:发生请求，与服务器建立连接
                    conn.connect();
                    //如果响应码为200，说明请求成功
                    if(conn.getResponseCode() == 200)
                    {

                        //获取服务器响应头中的流
                        InputStream is = conn.getInputStream();

                        //读取流里的数据，构建成bitmap位图
                        Bitmap bm = BitmapFactory.decodeStream(is);
                        bmg = bm;
                        //发生更新UI的消息
                        Message msg = handler.obtainMessage();
                        msg.obj = bm;

                        handler.sendMessage(msg);

                        //显示在界面上
                        //ImageView imageView = (ImageView) findViewById(R.id.lv);
                        //imageView.setImageBitmap(bm);

                    }else {
                        //发送获取失败的消息
                        Message msg = handler.obtainMessage();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        //启动线程任务
        thread.start();

    }




    private void callPolice() {
        String num = "110";//获取文本框内容(号码)
        //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+num));//创建意图对象
        Intent intent = new Intent();//创建意图对象
        intent.setAction(Intent.ACTION_CALL);//设置意图的动作为拨打电话
        intent.setData(Uri.parse("tel:" + num));//设置意图的数据(电话号码)
        startActivity(intent);//执行意图	}
    }





}









