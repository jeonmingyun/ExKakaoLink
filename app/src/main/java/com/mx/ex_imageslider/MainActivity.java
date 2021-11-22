package com.mx.ex_imageslider;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.mx.ex_kakaolink.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    private View imgSlider;
    private ViewPager2 imgSliderViewPager;
    private LinearLayout imgSliderIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        imgSlider = findViewById(R.id.image_slider);
        imgSliderViewPager = findViewById(R.id.img_slider_view_pager);
        imgSliderIndicator = findViewById(R.id.img_slider_indicators);

        ArrayList<String> itemList = new ArrayList<>();
        itemList.add("https:\\/\\/psgmall.com\\/upload_files\\/prd_img\\/202111\\/16365197477.jpg");
        itemList.add("https:\\/\\/psgmall.com\\/upload_files\\/prd_img\\/202111\\/16360862368.jpg");
        itemList.add("https:\\/\\/psgmall.com\\/upload_files\\/prd_img\\/202111\\/16360863615.jpg");
        setUpImageSlider(itemList);
    }

    private void setUpImageSlider(ArrayList<String> itemList) {
        imgSliderViewPager.setAdapter(new ImageSliderAdapter(this, itemList));
        imgSliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentImgSliderIndicator(position);
            }
        });
        /* if nested viewpager */
//        imgSliderViewPager.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                imgSliderViewPager.requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
        setupImgSliderIndicators(itemList.size());
    }


    private void setupImgSliderIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        imgSliderIndicator.removeAllViews();
        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,
                    R.drawable.indicator_img_slider_inactive));
            indicators[i].setLayoutParams(params);
            imgSliderIndicator.addView(indicators[i]);
        }
        setCurrentImgSliderIndicator(0);
    }

    private void setCurrentImgSliderIndicator(int position) {
        int childCount = imgSliderIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) imgSliderIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.indicator_img_slider_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        this,
                        R.drawable.indicator_img_slider_inactive
                ));
            }
        }
    }
}