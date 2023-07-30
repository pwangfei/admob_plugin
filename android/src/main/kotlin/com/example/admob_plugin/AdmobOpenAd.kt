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
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

/**
 * 开屏广告
 */
class AdmobOpenAd(
    private val flutterPluginBinding: FlutterPlugin.FlutterPluginBinding,
    val mActivity: Activity?
) : MethodChannel.MethodCallHandler {

    private var appOpenAd: AppOpenAd? = null
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "load" -> {
                val loadCallback: AppOpenAd.AppOpenAdLoadCallback =
                    object : AppOpenAd.AppOpenAdLoadCallback() {

                        override fun onAdLoaded(p0: AppOpenAd) {
                            super.onAdLoaded(p0)
                            appOpenAd = p0;
                            Log.e("wpf123wpf", "onAdLoaded: ======================")
                        }


                        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                            super.onAdFailedToLoad(loadAdError)
                            Log.e("wpf123wpf", "LoadAdError: ======================"+loadAdError.message)

                        }

                    }
                val request = AdRequest.Builder().build()
                AppOpenAd.load(
                    mActivity,
                    "ca-app-pub-3940256099942544/3419835294",
                    request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    loadCallback
                )
                Log.e("wpf123wpf", "load: ======================12222222222222")
                result.success(null)
            }
            "isLoaded" -> {
                val id = call.argument<Int>("id")
                result.success(true)
            }
            "show" -> {
                val id = call.argument<Int>("id")
                Log.e("wpf123wpf", "show: " + appOpenAd)
                appOpenAd?.show(mActivity)
            }
            "dispose" -> {
                val id = call.argument<Int>("id")

            }
            else -> result.notImplemented()
        }
    }


}
