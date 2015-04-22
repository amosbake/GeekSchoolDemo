package com.yanhao.tulingrobot;

/**
 * Created by yons on 2015/4/21.
 */
public class ListData {
    public static final int SEND=1;
    public static final int RECEIVER=2;
    private String content;
    private String time;
    private int flag;


    public ListData(String content, String time, int flag) {
        this.content = content;
        this.time = time;
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
