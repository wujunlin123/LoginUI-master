package cn.com.lttc.loginui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class addCamera extends AppCompatActivity implements View.OnClickListener {

    private Button call_Police;
    private  String imageUrl = "http://news.sciencenet.cn/upload/news/images/2011/3/20113301123128272.jpg";
    private  String path = "http://192.168.43.39:80/?action=stream";
    private Button button,Imagebutton;
    private ImageView img;
    private Bitmap bm;
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
        }

    }

    private void getImageThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                while(true){
                    getImage();
                    try {
                        Thread.sleep(50);
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
//    private void getImage() {
//        //新建线程加载图片信息，发送到消息队列中
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                Bitmap bmp = getURLimage(imageUrl);
//                Message msg = new Message();
//                msg.what = 0;
//                msg.obj = bmp;
//                handle.sendMessage(msg);
//            }
//        }).start();
//    }

    //wjl
//private void getImage() {
//    //新建线程加载图片信息，发送到消息队列中
//    new Thread(new Runnable() {
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            Bitmap bmp = getURLimage(path);
//            Message msg = new Message();
//            msg.what = 0;
//            msg.obj = bmp;
//            handle.sendMessage(msg);
//        }
//    }).start();
//}
//
//private Bitmap getURLimage(String imageUrl) {
//    Bitmap bmp = null;
//    try {
//        URL myurl = new URL(imageUrl);
//        // 获得连接
//        HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
//        conn.setConnectTimeout(6000);//设置超时
//        conn.setDoInput(true);
//        conn.setUseCaches(false);//不缓存
//        conn.connect();
//        InputStream is = conn.getInputStream();//获得图片的数据流
//        bmp = BitmapFactory.decodeStream(is);
//        is.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return bmp;
//}


    //wjl
//private Bitmap getURLimage(String path) {
//    Bitmap bmp = null;
//    try {
//        URL myurl = new URL(path);
//        // 获得连接
//        HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
//        conn.setConnectTimeout(6000);//设置超时
//        conn.setDoInput(true);
//        conn.setUseCaches(false);//不缓存
//       // conn.getHeaderField("filename");
//       // conn.setRequestProperty("Header","?action=stream");
//        conn.connect();
//        InputStream is = conn.getInputStream();//获得图片的数据流
//        bmp = BitmapFactory.decodeStream(is);
//        is.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return bmp;
//}

    private void callPolice() {
        String num = "110";//获取文本框内容(号码)
        //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+num));//创建意图对象
        Intent intent = new Intent();//创建意图对象
        intent.setAction(Intent.ACTION_CALL);//设置意图的动作为拨打电话
        intent.setData(Uri.parse("tel:" + num));//设置意图的数据(电话号码)
        startActivity(intent);//执行意图	}
    }

    //    public void onClick(View v) {
//        String num = "110";//获取文本框内容(号码)
//        //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+num));//创建意图对象
//        Intent intent = new Intent();//创建意图对象
//        intent.setAction(Intent.ACTION_CALL);//设置意图的动作为拨打电话
//        intent.setData(Uri.parse("tel:" + num));//设置意图的数据(电话号码)
//        startActivity(intent);//执行意图	}
//
//        //新建线程加载图片信息，发送到消息队列中
//               new Thread(new Runnable() {
//                   @Override
//                   public void run() {
//                       // TODO Auto-generated method stub
//                      Bitmap bmp = getURLimage(imageUrl);
//                      Message msg = new Message();
//                      msg.what = 0;
//                      msg.obj = bmp;
//                      handle.sendMessage(msg);
//                   }
//               }).start();



    };









