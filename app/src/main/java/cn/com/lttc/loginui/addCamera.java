package cn.com.lttc.loginui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class addCamera extends AppCompatActivity implements OnClickListener {

    private Button call_Police;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_camera);
        super.onCreate(savedInstanceState);
        call_Police = (Button) findViewById(R.id.callPolice);
        call_Police.setOnClickListener(this);

    }


    public void onClick(View v) {
        String num = "110";//获取文本框内容(号码)
        //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+num));//创建意图对象
        Intent intent = new Intent();//创建意图对象
        intent.setAction(Intent.ACTION_CALL);//设置意图的动作为拨打电话
        intent.setData(Uri.parse("tel:" + num));//设置意图的数据(电话号码)
        startActivity(intent);//执行意图	}

    };
}





