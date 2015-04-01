package com.yanhao.myphonedirectory;

/**
 * Created by yons on 2015/3/31.
 */
public class PhoneInfo {
    private String name;
    private String phoneNumber;

    public PhoneInfo() {
    }

    public PhoneInfo(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
