package com.example.admob_plugin

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.platform.PlatformView
import java.util.*


class AdmobNative(context: Context, messenger: BinaryMessenger, id: Int) :
    PlatformView {

    private var view: View? = null
    private var mNativeAd: NativeAd? = null
    init {
        //这个xml一定不能复用
        view = LayoutInflater.from(context).inflate(R.layout.view_native, null, true)

        val builder = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad: NativeAd ->
                mNativeAd=ad;


                //展示yuan
                val adView = view?.findViewById<NativeAdView>(R.id.native_adview)
                val headlineView = view?.findViewById<TextView>(R.id.nativeName)
                headlineView?.text = mNativeAd?.headline
                adView?.headlineView = headlineView
                val mediaView = adView?.findViewById<MediaView>(R.id.nativeMedia)
                adView?.mediaView = mediaView
                adView?.setNativeAd(mNativeAd)
            }
        val videoOptions = VideoOptions.Builder().setStartMuted(false).build()
        val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions)
            .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_LEFT).build()
        builder.withNativeAdOptions(adOptions)

        val adLoader = builder
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // Handle the failure by logging, altering the UI, and so on.
                }
            })
            .build()
        adLoader.loadAd(AdRequest.Builder().setHttpTimeoutMillis(30000).build())



    }


    override fun getView(): View? {
        return view
    }

    override fun dispose() {

    }

}