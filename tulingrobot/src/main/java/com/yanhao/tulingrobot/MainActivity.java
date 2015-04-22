package com.yanhao.tulingrobot;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends ActionBarActivity implements HttpGetDataListener,View.OnClickListener{
    private static final String TAG="MainActivity";
    private static final String TESTURL="http://www.tuling123.com/openapi/api?key=4eb64c05eeafa9b2deead26143466a7d&info=";
    private HttpData httpData;
    private List<ListData> lists;
    private String content_str;
    private String[] welcome_array;
    private long currentTime,oldTime=0;

    private TextAdapter adapter;
    private ListView lv_message;
    private EditText et_text;
    private Button btn_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        lists=new ArrayList<>();
        lists.add(new ListData(getRandomWelcomeTips(),getTime(),ListData.RECEIVER));
        lv_message= (ListView) findViewById(R.id.lv);
        et_text= (EditText) findViewById(R.id.send_text);
        btn_send= (Button) findViewById(R.id.send_btn);
        btn_send.setOnClickListener(this);
        adapter=new TextAdapter(lists,this);
        lv_message.setAdapter(adapter);

        btn_send.setOnClickListener(this);


    }
    //处理获取到的结果
    @Override
    public void getDataUrl(String data) {
//        Log.i(TAG,data);
        parseText(data);
    }
    public void parseText(String data){
        try {
            JSONObject object=new JSONObject(data);
            ListData listData=new ListData(object.getString("text"),getTime(),ListData.RECEIVER);
            lists.add(listData);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    //获取随机欢迎语
    private String getRandomWelcomeTips(){
        String  welcome_tip=null;
        welcome_array=this.getResources().getStringArray(R.array.welcome_tips);
        int index=(int)(Math.random()*welcome_array.length-1);
        welcome_tip=welcome_array[index];
        return welcome_tip;
    }

    private String getTime(){
        currentTime=System.currentTimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate=new Date();
        String str=format.format(curDate);
        if (currentTime-oldTime>=5*60*1000){
            oldTime=currentTime;
            return str;
        }else {
            return "";
        }
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(et_text.getText())){
           return;
        }
        content_str=et_text.getText().toString().trim();
        ListData listData=new ListData(content_str,getTime(),ListData.SEND);
        lists.add(listData);
        Log.i(TAG,content_str);
        adapter.notifyDataSetChanged();
        httpData= (HttpData) new HttpData(TESTURL+content_str,this).execute();
        et_text.setText("");

        if (lists.size()>30){
            for (int i=0;i<lists.size();i++){
                if (i<10){
                    lists.remove(i);
                }
            }
        }
    }
}
