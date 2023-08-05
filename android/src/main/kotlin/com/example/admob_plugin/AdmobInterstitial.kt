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

    private var adLog="ADLog";

    class InterstitialAdInfo(var interstitialAd: InterstitialAd?, var time: Long)

    /**
     * 支持多插页的情况
     */
    companion object {
        var allAds: MutableMap<String, InterstitialAdInfo?> = mutableMapOf()
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {

            "load" -> {
                var adUnitId = call.argument<String>("adUnitId")
                load(adUnitId, object : AdCallback {
                    override fun success() {
                        result.success(true)
                    }

                    override fun fail() {
                        result.success(false)
                    }
                })

            }
            "isLoaded" -> {
                checkExpired()
                var adUnitId = call.argument<String>("adUnitId")
                var mInterstitialAd = allAds[adUnitId]?.interstitialAd
                var isSuccess = false
                if (mInterstitialAd != null) {
                    isSuccess = true;
                }
                result.success(isSuccess)
            }
            "show" -> {
                var adUnitId = call.argument<String>("adUnitId")
                var mInterstitialAd = allAds[adUnitId]?.interstitialAd
                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.e(adLog, "AdmobInterstitial The ad was dismissed.")
                        mInterstitialAd = null
                        load(adUnitId, null);
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        mInterstitialAd = null
                        result.success(false)
                        Log.e(adLog, "AdmobInterstitial The ad failed to show.")
                        load(adUnitId, null);
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.e(adLog, "AdmobInterstitial The ad was shown.")

                        result.success(true)

                    }
                }
                mInterstitialAd?.show(mActivity)
            }

            else -> result.notImplemented()
        }
    }


    fun load(adUnitId: String?, adCallback: AdCallback?) {
        val adChannel =
            MethodChannel(flutterPluginBinding.binaryMessenger, "admob_flutter/interstitial")
        val adRequest: AdRequest = AdRequest.Builder().build()
        InterstitialAd.load(mActivity, adUnitId, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    adChannel.invokeMethod("loaded", null)
                    adCallback?.success()
                    var interstitialAdInfo=InterstitialAdInfo(interstitialAd,System.currentTimeMillis());
                    allAds[adUnitId ?: ""] = interstitialAdInfo;
                    Log.e(adLog, "AdmobInterstitial onAdLoaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e(adLog, loadAdError.message)
                    allAds[adUnitId ?: ""] = null;
                    adChannel.invokeMethod("AdmobInterstitial failedToLoad", null)
                    adCallback?.fail()
                }

            })
    }

    /**
     * 检查是否要进行超时清理
     */
    fun checkExpired() {
        for ((key, value) in allAds) {
           var time= value?.time?:System.currentTimeMillis()
            if(System.currentTimeMillis()-time>1000*60*60){//超时了要进行清理下
                allAds[key]=null;
            }
        }
    }

}
