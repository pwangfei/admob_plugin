package com.example.admob_plugin

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.google.android.gms.ads.*
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.platform.PlatformView


class AdmobMixBanner(
    val context: Context,
    messenger: BinaryMessenger,
    id: Int,
) :
    PlatformView {

    private var adViewContainer: FrameLayout? = null


    init {
        //这个xml一定不能复用
        adViewContainer = LayoutInflater.from(context)
            .inflate(R.layout.contain_layout, null, false) as FrameLayout?

        // 计算屏幕宽度
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics

        val screenWidthPixels = displayMetrics.widthPixels
        val density = displayMetrics.density
        val screenWidthDp = (screenWidthPixels / density)
        val adSize = AdSize(screenWidthDp.toInt(), 50)

        var adView = AdView(context)
        adView?.setAdUnitId("ca-app-pub-3940256099942544/6300978111")
        adViewContainer?.addView(adView)
        val adRequest = AdRequest.Builder().build()
        adView?.setAdSize(adSize)
        adView?.loadAd(adRequest)
        adView?.setAdListener(object : AdListener() {
            override fun onAdLoaded() {

                Log.e("wpf123wpf", "AdmobMixBanner onAdLoaded: ")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {

                Log.e("wpf123wpf", "AdmobMixBanner onAdFailedToLoad: " + adError.toString())

            }

            override fun onAdOpened() {

                Log.e("wpf123wpf", "AdmobMixBanner onAdOpened: ")

            }

            override fun onAdClicked() {

                Log.e("wpf123wpf", "AdmobMixBanner onAdClicked: ")
            }

            override fun onAdClosed() {

                Log.e("wpf123wpf", "AdmobMixBanner onAdClosed: ")
            }
        })

    }


    override fun getView(): View? {
        return adViewContainer
    }

    override fun dispose() {

    }


}