package com.mx.ex_kakaolink;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String title = "타이틀";
    String subTitle = "서브 타이틀";
    String img = "https://c.011st.com/img/common/v3/logoday.png";
    String link = "https://www.11st.co.kr/products/3923670878";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.share_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*1. java 사용 코드*/
                kakaoLinkJava(title, subTitle, img, link);
                /*2. 코틀린 사용 코드*/
//                Intent kakao_intent = new Intent(MainActivity.this, KakaoLinkKotlinAct.class);
//                kakao_intent.putExtra("title", title);
//                kakao_intent.putExtra("subTitle", title);
//                kakao_intent.putExtra("img", img);
//                kakao_intent.putExtra("link", link);
//                startActivity(kakao_intent);
            }
        });

        getHashKey();

        if(getIntent() != null && getIntent().getData() != null) {
            Toast.makeText(this, " " + getIntent().getData().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void kakaoLinkJava(String title, String subTitle, String img, String link) {
        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder(title,
                        img,
                        LinkObject.newBuilder()
                                .setWebUrl(link)
                                .setMobileWebUrl(link)
                                .build())
                        .setDescrption(subTitle)
                        .build())
                .addButton(new ButtonObject("자세히 보기", LinkObject.newBuilder()
                        .setWebUrl(link)
                        .setMobileWebUrl(link)
                        .build()))
                /*버튼 추가 하여 사용 가능*/
//                .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
//                        .setWebUrl(link)
//                        .setMobileWebUrl(link)
//                        .setAndroidExecutionParams("key=value")
//                        .setIosExecutionParams("key=value")
//                        .build()))
                .build();

//        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
//        serverCallbackArgs.put("key", value);

        KakaoLinkService.getInstance().sendDefault(this, params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("kakao_is_fail", errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                Log.e("kakao_is_success", result.toString());
            }
        });

    }

    private void getHashKey() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }

}