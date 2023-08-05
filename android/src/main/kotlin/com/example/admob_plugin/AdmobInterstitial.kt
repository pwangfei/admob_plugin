package com.example.admob_plugin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

/**
 * 插页广告
 */
class AdmobInterstitial(
    private val flutterPluginBinding: FlutterPlugin.FlutterPluginBinding,
    val mActivity: Activity?
) : MethodChannel.MethodCallHandler {

    private var mInterstitialAd: InterstitialAd? = null

    private var adUnitId:String?=null
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {

            "load" -> {
                adUnitId = call.argument<String>("adUnitId")
                load(adUnitId,object :AdCallback{
                    override fun success() {
                        result.success(true)
                    }

                    override fun fail() {
                        result.success(false)
                    }
                })

            }
            "isLoaded" -> {
                var isSuccess = false
                if (mInterstitialAd != null) {
                    isSuccess = true;
                }
                result.success(isSuccess)
            }
            "show" -> {
                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.e("wpf123wpf", "AdmobInterstitial The ad was dismissed.")
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        mInterstitialAd = null
                        result.success(false)
                        Log.e("wpf123wpf", "AdmobInterstitial The ad failed to show.")
                        load(adUnitId,null);
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.e("wpf123wpf", "AdmobInterstitial The ad was shown.")
                        mInterstitialAd = null
                        result.success(true)
                        load(adUnitId,null);
                    }
                }
                mInterstitialAd?.show(mActivity)
            }

            else -> result.notImplemented()
        }
    }


    fun load(adUnitId: String?,adCallback:AdCallback?) {
        val adChannel =
            MethodChannel(flutterPluginBinding.binaryMessenger, "admob_flutter/interstitial")
        val adRequest: AdRequest = AdRequest.Builder().build()
        InterstitialAd.load(mActivity, adUnitId, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    adChannel.invokeMethod("loaded", null)
                    adCallback?.success()
                    Log.e("wpf123wpf", "AdmobInterstitial onAdLoaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e("wpf123wpf", loadAdError.message)
                    mInterstitialAd = null
                    adChannel.invokeMethod("AdmobInterstitial failedToLoad", null)
                    adCallback?.fail()
                }

            })
    }

}
