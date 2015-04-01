package com.yanhao.myphonedirectory;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yons on 2015/3/31.
 */
public class GetPhoneNumber {
    public static List<PhoneInfo> phoneInfoList=new ArrayList<>();
    public static List<PhoneInfo> getNumber(Context context){
        Cursor cursor=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        String phoneNumber;
        String phoneName;
        while (cursor.moveToNext()){
            phoneNumber=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneName=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            PhoneInfo info=new PhoneInfo(phoneName,phoneNumber);
            phoneInfoList.add(info);
            Log.i("number:",phoneName+":"+phoneNumber);
        }
        return phoneInfoList;
    }
}
