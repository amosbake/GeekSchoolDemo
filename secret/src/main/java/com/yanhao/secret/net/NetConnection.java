package com.yanhao.secret.net;

import android.os.AsyncTask;
import android.util.Log;
import com.yanhao.secret.Config;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yons on 2015/4/22.
 */
public class NetConnection {
    private static final String TAG="NetConnection";
    public NetConnection(final String url, final HttpMethod method, final SuccessCallback successCallback, final FailCallback failCallback, final String... kvs) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                StringBuffer paramString=new StringBuffer();
                for (int i=0;i<kvs.length;i+=2){
                    paramString.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
                }

                try {
                    URLConnection uc =null;
                    switch (method) {
                        //set Connection Post
                        case POST:
                            uc=new URL(url).openConnection();
                            uc.setDoOutput(true);
                            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(),Config.CHARSET));
                            bw.write(paramString.toString());
                            break;
                        //set Connection Get
                        case GET:
                            uc=new URL(url+"?"+paramString.toString()).openConnection();
                            break;
                    }
                    Log.i(TAG,"Request url:"+uc.getURL());
                    Log.i(TAG,"Request data:"+paramString.toString());
                    BufferedReader br=new BufferedReader(new InputStreamReader(uc.getInputStream(),Config.CHARSET));
                    String line=null;
                    StringBuffer result=new StringBuffer();
                    while ((line=br.readLine())!=null){
                        result.append(line);
                    }

                    Log.i(TAG,"result "+result.toString());
                    return result.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result!=null){
                    if (successCallback!=null){
                        successCallback.onSuccess(result);
                    }
                }else{
                    if (failCallback!=null){
                        failCallback.onFail();
                    }
                }
                super.onPostExecute(result);
            }
        };
    }
    //do what for success
    public static interface SuccessCallback {
        void onSuccess(String result);
    }
    //do what for fail
    public static interface FailCallback {
        void onFail();
    }
}
