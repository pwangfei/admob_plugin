package com.example.admob_plugin

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.google.android.gms.ads.*
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.platform.PlatformView
import java.util.*


class AdmobMixBanner(context: Context, messenger: BinaryMessenger, id: Int) :
    PlatformView {

    private var adViewContainer: FrameLayout? = null

    init {
        //这个xml一定不能复用
        adViewContainer = LayoutInflater.from(context).inflate(R.layout.contain_layout, null, true) as FrameLayout?
        var adView = AdView(context)
        adView?.setAdUnitId("ca-app-pub-3940256099942544/6300978111")
        adViewContainer?.addView(adView)
        val adRequest = AdRequest.Builder().build()
        adView?.setAdSize(AdSize.BANNER)
        adView?.loadAd(adRequest)
        adView?.setAdListener(object : AdListener() {
            override fun onAdLoaded() {

                Log.e("wpf123wpf", "onAdLoaded: ")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {

                Log.e("wpf123wpf", "onAdFailedToLoad: " + adError.toString())

            }

            override fun onAdOpened() {

                Log.e("wpf123wpf", "onAdOpened: ")

            }

            override fun onAdClicked() {

                Log.e("wpf123wpf", "onAdClicked: ")
            }

            override fun onAdClosed() {

                Log.e("wpf123wpf", "onAdClosed: ")
            }
        })

    }


    override fun getView(): View? {
        return adViewContainer
    }

    override fun dispose() {

    }

}