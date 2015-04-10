package com.yanhao.hellonotes;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.w3c.dom.Text;

import static com.yanhao.hellonotes.R.id.list_time;

/**
 * Created by yons on 2015/4/7.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private Cursor cursor;

    public MyAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return cursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        if (convertView==null){
            convertView=inflater.inflate(R.layout.list_note_items,null);
            TextView contentTv= (TextView) convertView.findViewById(R.id.list_content);
            TextView timeTv= (TextView) convertView.findViewById(R.id.list_time);
            ImageView imgiv= (ImageView) convertView.findViewById(R.id.list_img);
            ImageView videoiv= (ImageView) convertView.findViewById(R.id.list_video);
            cursor.moveToPosition(position);
            String content=cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT));
            String time=cursor.getString(cursor.getColumnIndex(NotesDB.TIME));
            String url=cursor.getString(cursor.getColumnIndex(NotesDB.PATH));
            String uriVideo=cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO));
            contentTv.setText(content);
            timeTv.setText(time);
            if(url!=null){
                imgiv.setImageBitmap(getImageThumbnail(url,100,100));
                videoiv.setVisibility(View.GONE);
            }
            if (uriVideo!=null){
                videoiv.setImageBitmap(getViedoThumbnail(uriVideo,100,100));
                imgiv.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    private Bitmap getViedoThumbnail(String uriVideo, int width, int height) {
        Bitmap bitmap= null;
        bitmap=ThumbnailUtils.createVideoThumbnail(uriVideo,MediaStore.Images.Thumbnails.MICRO_KIND);
        bitmap=ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    public Bitmap getImageThumbnail(String uri,int width,int height){
        Bitmap bitmap= null;
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        bitmap=BitmapFactory.decodeFile(uri,options);
        int beWidth=options.outWidth/width;
        int beHeight=options.outHeight/height;
        int be=1;
        if (beWidth<beHeight){
            be=beWidth;
        }else {
            be=beHeight;
        }
        if (be<=0){
            be=1;
        }
        options.inSampleSize=be;
        options.inJustDecodeBounds=false;
        bitmap=BitmapFactory.decodeFile(uri,options);
        bitmap=ThumbnailUtils.extractThumbnail(bitmap,width,height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
}
