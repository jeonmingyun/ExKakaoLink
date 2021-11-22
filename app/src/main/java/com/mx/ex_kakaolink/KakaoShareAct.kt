package com.mx.ex_kakaolink

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import com.kakao.sdk.talk.TalkApiClient
import com.kakao.sdk.template.model.*

class KakaoShareAct : AppCompatActivity() {

    private var pid : String? = ""
    private var img : String? = ""
    private var product_name : String? = ""
    private var link : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.getStringExtra("pid") != null){
//            productVo = ProductVo().fromJson(intent.getStringExtra("data")) as ProductVo
//            kakaoLink(productVo!!.img, productVo!!.name, productVo!!.price_sell.toString())

            pid = intent.getStringExtra("pid") as String
            img = intent.getStringExtra("img") as String
            product_name = intent.getStringExtra("product_name") as String
            link = intent.getStringExtra("link") as String

            kakaoLink(img!!, product_name!!, link!!)
        } else if(intent.getStringExtra("Channal") != null){

            val url = TalkApiClient.instance.addChannelUrl("_VvxoLs")

            KakaoCustomTabsClient.openWithDefault(this, url)
        }

        finish()

    }


    private fun kakaoLink(imageUrl : String, productName : String, link : String) {
        Log.e("on", "$pid / $productName / $imageUrl / $link");
        // 공유하기 컨텐츠 설정
        val defaultFeed = FeedTemplate(
                content = Content(
                        title = productName, // 상품이름
//                description = productPrice, // 상품설명
                        imageUrl = imageUrl, // Image 설정
                        link = Link( // 이미지 눌렀을시 처리할 URL 설정
                                webUrl = link,
                                mobileWebUrl = link
                        )
                ),
                itemContent = ItemContent(
                ),
                buttons = listOf( // 버튼 설정
                        Button(
                                "자세히보기",
                                Link( // 버튼 눌렀을시 처리할 URL 설정
                                        webUrl = link,
                                        mobileWebUrl = link
                                )
                        )
                )
        )

        // 카카오톡 설치여부 확인
        if (LinkClient.instance.isKakaoLinkAvailable(this)) {
            // 카카오톡으로 카카오링크 공유 가능
            LinkClient.instance.defaultTemplate(this, defaultFeed) { linkResult, error ->
                if (error != null) {
                    Log.e("on", "카카오링크 보내기 실패", error)
                }
                else if (linkResult != null) {
                    Log.d("on", "카카오링크 보내기 성공 ${linkResult.intent}")
                    startActivity(linkResult.intent)
                    // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    finish()
                    Log.w("on", "Warning Msg: ${linkResult.warningMsg}")
                    Log.w("on", "Argument Msg: ${linkResult.argumentMsg}")

                }
            }
        } else {
            // 카카오톡 미설치: 웹 공유 사용 권장
            // 웹 공유 예시 코드
            val sharerUrl = WebSharerClient.instance.defaultTemplateUri(defaultFeed)

            // CustomTabs으로 웹 브라우저 열기

            // 1. CustomTabs으로 Chrome 브라우저 열기
//            try {
//                KakaoCustomTabsClient.openWithDefault(this, sharerUrl)
//            } catch(e: UnsupportedOperationException) {
//                // Chrome 브라우저가 없을 때 예외처리
//            }

            // 2. CustomTabs으로 디바이스 기본 브라우저 열기
            try {
                KakaoCustomTabsClient.open(this, sharerUrl)
            } catch (e: ActivityNotFoundException) {
                // 인터넷 브라우저가 없을 때 예외처리
            }
        }
    }

}