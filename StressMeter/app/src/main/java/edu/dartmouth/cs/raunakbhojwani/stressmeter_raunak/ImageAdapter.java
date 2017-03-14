package edu.dartmouth.cs.raunakbhojwani.stressmeter_raunak;

import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;

/**
 * Created by RaunakBhojwani on 1/25/17.
 * Influenced and guided by: https://developer.android.com/guide/topics/ui/layout/gridview.html#example
 */

public class ImageAdapter extends BaseAdapter {

    public final static String TAG = "DebugTag";

    private Context context;
    private Integer mGridNumber;

    public ImageAdapter(Context c, Integer gridNumber) {
        context = c;
        mGridNumber = gridNumber;
    }

    public int getCount() {
        Log.d(TAG, mGridNumber.toString());
        return PSM.getGridById(mGridNumber).length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView mImageView;

        if (convertView == null) {
            mImageView = new ImageView(context);
            mImageView.setLayoutParams(new GridView.LayoutParams(350, 350));
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        else {
            mImageView = (ImageView) convertView;
        }

        mImageView.setImageResource(PSM.getGridById(mGridNumber)[position]);
        return mImageView;
    }
}
