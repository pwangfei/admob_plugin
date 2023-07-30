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
        val adRequest: AdRequest = AdRequest.Builder().build()
        InterstitialAd.load(mActivity, "ca-app-pub-3940256099942544/1033173712", adRequest,
          object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(interstitialAd: InterstitialAd) {

              mInterstitialAd = interstitialAd
              Log.e("wpf123wpf", "onAdLoaded")
            }

            override fun onAdFailedToLoad( loadAdError: LoadAdError) {
              // Handle the error
              Log.e("wpf123wpf", loadAdError.message)
              mInterstitialAd = null
            }

          })

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
          override fun onAdDismissedFullScreenContent() {

            Log.e("wpf123wpf", "The ad was dismissed.")
          }

          override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            // Called when fullscreen content failed to show.
            Log.e("wpf123wpf", "The ad failed to show.")
          }

          override fun onAdShowedFullScreenContent() {
            mInterstitialAd = null
            Log.e("wpf123wpf", "The ad was shown.")
          }
        }
        Log.e("wpf123wpf", "load: ======================12222222222222")
        result.success(null)
      }
      "isLoaded" -> {
        val id = call.argument<Int>("id")
        result.success(true)
      }
      "show" -> {
        val id = call.argument<Int>("id")
        Log.e("wpf123wpf", "show: "+mInterstitialAd)
        mInterstitialAd?.show(mActivity)
      }
      "dispose" -> {
        val id = call.argument<Int>("id")

      }
      else -> result.notImplemented()
    }
  }


}
