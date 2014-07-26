package com.imagescroll.horizontalscrollimage.uiutil;

import com.imagescroll.horizontalscrollimage.R;

import android.widget.LinearLayout;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
public class TextViewImage extends LinearLayout {
 
    private ImageView mHeroIcon;
    private ImageView mHeroTitle;
    private TextView mHeroName;
 
    public TextViewImage(Context context) {
        super(context);
 
        loadViews();
    }
 
    public TextViewImage(Context context, AttributeSet attrs) {
        super(context, attrs);
 
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.image_and_text, this);
 
        loadViews();
    }
 
    private void loadViews() {
        //mHeroIcon = (ImageView)findViewById(R.id.ivIcon);
        //mHeroTitle = (ImageView)findViewById(R.id.ivTitle);
        //mHeroName = (TextView)findViewById(R.id.tvName);
    }
 
    public void setHeroIcon(Drawable icon) {
       // mHeroIcon.setImageDrawable(icon);
    }
 
    public void setHeroTitle(Drawable title) {
       // mHeroTitle.setImageDrawable(title);
    }
 
    public void setHeroName(String name) {
      //  mHeroName.setText("::" + name + "::");
    }
 
}