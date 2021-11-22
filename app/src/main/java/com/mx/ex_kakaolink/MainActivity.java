package com.mx.ex_kakaolink;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

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
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getHashKey();

    }

    public void btnClick(View view) {
//        Log.e("dddddd", "clicked");
//        Intent kakao_intent = new Intent(this, KakaoShareAct.class);
//        kakao_intent.putExtra("pid", "4");
//        kakao_intent.putExtra("product_name", "product_name");
//        kakao_intent.putExtra("img", "https://image.genie.co.kr/Y/IMAGE/IMG_ALBUM/081/191/791/81191791_1555664874860_1_600x600.JPG");
//        kakao_intent.putExtra("link", "https://psgmall.com");
//        startActivity(kakao_intent);
//        2138 /  / https://psgmall.com/upload_files/prd_img/202111/163651980510.jpg / https://psgmall.com/api/link.php?no=2138
        String title = "테스트용 - 구매불가 - 상품5";
        String subTitle = "서브 타이틀";
        String imgUrl = "https://psgmall.com/upload_files/prd_img/202111/163651980510.jpg";
        String pid = "2138";

        String link1 = "https://psgmall.com";
        String link2 = "intent://product?no=2138";
        String link3 = "https://psgmall.com/pages/shop/view.php?prd_id=50";
        String link4 = "intent://product";
        String link5 = "https://www.naver.com";
        String link6 = "https://psgmall.com/api/link.php";
        String link7 = "https://psgmall.com/api/link.php?no=2138";

        String webLink = link1;
        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder(title,
                        imgUrl,
                        LinkObject.newBuilder()
                                .setWebUrl(link1)
                                .setMobileWebUrl(link1)
                                .build())
//                        .setDescrption(subTitle)
                        .build())
                .addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder()
                        .setWebUrl(link7)
                        .setMobileWebUrl(link7)
                        .build()))
                .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                        .setWebUrl(link6)
                        .setMobileWebUrl(link6)
                        .setAndroidExecutionParams("no=2138")
//                        .setIosExecutionParams("no=2                  138")
                        .build()))
                .build();

//        Map<String, String> serverCallbackArgs = new HashMap<String, String>();
//        serverCallbackArgs.put("no", pid);


        KakaoLinkService.getInstance().sendDefault(this, params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("kakao_is_success", errorResult.toString());
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                Log.e("kakao_is_fail", result.toString());
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