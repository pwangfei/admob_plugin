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
class AdmobInterstitial(private val flutterPluginBinding: FlutterPlugin.FlutterPluginBinding,val mActivity: Activity?): MethodChannel.MethodCallHandler {

  private var mInterstitialAd: InterstitialAd? = null
  override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
    when(call.method) {

      "load" -> {

        val adChannel = MethodChannel(flutterPluginBinding.binaryMessenger, "admob_flutter/interstitial")
        val adUnitId = call.argument<String>("adUnitId")
        val adRequest: AdRequest = AdRequest.Builder().build()
        InterstitialAd.load(mActivity, adUnitId, adRequest,
          object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(interstitialAd: InterstitialAd) {

              mInterstitialAd = interstitialAd

              adChannel.invokeMethod("loaded", null)
              result.success(true)
              Log.e("wpf123wpf", "onAdLoaded")
            }

            override fun onAdFailedToLoad( loadAdError: LoadAdError) {
              // Handle the error
              Log.e("wpf123wpf", loadAdError.message)
              mInterstitialAd = null
              adChannel.invokeMethod("failedToLoad", null)
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
            mInterstitialAd = null
            Log.e("wpf123wpf", "The ad was dismissed.")
          }

          override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            mInterstitialAd = null
            result.success(false)
            Log.e("wpf123wpf", "The ad failed to show.")
          }

          override fun onAdShowedFullScreenContent() {
            Log.e("wpf123wpf", "The ad was shown.")
            result.success(true)
          }
        }
        mInterstitialAd?.show(mActivity)
      }

      else -> result.notImplemented()
    }
  }


}
