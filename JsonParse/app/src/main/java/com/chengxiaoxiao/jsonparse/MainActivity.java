package com.chengxiaoxiao.jsonparse;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{

    private static final int SUCCESS = 1;
    private static final int FAILED = 0;
    private EditText editNumber;
    private TextView textResult;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case SUCCESS:

                    Person obj = (Person) msg.obj;

                    RetData retData = obj.getRetData();

                    String address = retData.getAddress();
                    String birthday = retData.getBirthday();
                    String sex = retData.getSex();

                    textResult.setText(address+"\t"+birthday+"\t"+sex);

                    break;
                case FAILED:
                    Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNumber = (EditText) findViewById(R.id.editNumber);
        textResult = (TextView) findViewById(R.id.textResult);
    }

    public void select(View v)
    {
        final String number = editNumber.getText().toString().trim();

        System.out.print(number);
        new Thread()
        {

            @Override
            public void run()
            {
                Message msg =Message.obtain();
                try
                {
                    //封装url
                    URL url = new URL("http://apis.baidu.com/apistore/idservice/id?id=" + number);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    //设置请求的信息
                    conn.setConnectTimeout(19600);
                    conn.setRequestMethod("GET");
                    //设置请求头
                    conn.setRequestProperty("apikey", "1830c3ce10bbaa866db6cf0aa1f1cd2e");


                    int responseCode = conn.getResponseCode();

                    if (responseCode == 200)
                    {
                        InputStream inputStream = conn.getInputStream();
                        //输入流解析为字符串
                        String json = StreamTool.decodeStream(inputStream);

                        System.out.printf(json);
                        //json字符串转换为json对象
                        JSONObject obj = new JSONObject(json);

                        Person p = new Person();

                        //obj的getString方法可以获取指定的数据
                        p.setErrNum(obj.getString("errNum"));
                        p.setRetMsg(obj.getString("retMsg"));

                        RetData data = new RetData();

                        JSONObject retData = obj.getJSONObject("retData");
                        data.setAddress(retData.getString("address"));
                        data.setBirthday(retData.getString("birthday"));
                        data.setSex(retData.getString("sex"));

                        p.setRetData(data);


                        msg.what = SUCCESS;
                        msg.obj = p;
                        handler.sendMessage(msg);
                    }
                    else
                    {
                        msg.what = FAILED;

                        handler.sendMessage(msg);
                    }


                } catch (Exception e)
                {
                    msg.what = FAILED;

                    handler.sendMessage(msg);
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
