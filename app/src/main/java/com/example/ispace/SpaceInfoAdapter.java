package com.example.ispace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class SpaceInfoAdapter extends BaseAdapter {
    Context currentContext;
    String info[];
    int ispace_info_images[];
    LayoutInflater inflater;
    public SpaceInfoAdapter(Context context,String info[], int ispace_info_images[]){
        this.currentContext = context;
        this.ispace_info_images = ispace_info_images;
        this.info = info;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return info.length;
    }

    @Override
    public Object getItem(int i) {
        return info[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_main_space_info,null);
        ImageView spaceInfoImage = (ImageView) view.findViewById(R.id.space_info_image);
        spaceInfoImage.setClipToOutline(true);
        spaceInfoImage.setImageResource(ispace_info_images[i]);
        TextView space_info_text = (TextView) view.findViewById(R.id.space_info_title);
        space_info_text.setText(info[i]);
        return view;


    }
}
