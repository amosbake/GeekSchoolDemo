package com.yanhao.secret.net;

import com.yanhao.secret.Config;

/**
 * Created by yons on 2015/4/25.
 */
public class GetCode {
    public GetCode(String phone, SuccessCallBack successCallBack, FailCallBack failCallBack) {
        new NetConnection(Config.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {

            }
        }, new NetConnection.FailCallback() {
            @Override
            public void onFail() {

            }
        }, phone);
    }

    public static interface SuccessCallBack {
        void onSuccess();
    }

    public static interface FailCallBack {
        void onFail();
    }
}
