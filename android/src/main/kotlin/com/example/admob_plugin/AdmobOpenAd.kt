package com.example.admob_plugin

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * 开屏广告，只支持一个开屏广告
 */
class AdmobOpenAd(
    private val flutterPluginBinding: FlutterPlugin.FlutterPluginBinding,
    val mActivity: Activity?
) : MethodChannel.MethodCallHandler {

    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    private var isShowingAd = false
    private var loadTime=0L;

    private var adLog="ADLog";

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "load" -> {
                val adChannel = MethodChannel(flutterPluginBinding.binaryMessenger, "admob_flutter/apenad")
                if(appOpenAd!=null){//缓存有开屏了
                    adChannel.invokeMethod("onAdLoaded", null)
                    result.success(true)
                    return
                }
                if(isLoadingAd){//正在加载
                    return
                }
                isLoadingAd = true;
                val adUnitId = call.argument<String>("adUnitId")
                val loadCallback: AppOpenAd.AppOpenAdLoadCallback =
                    object : AppOpenAd.AppOpenAdLoadCallback() {

                        override fun onAdLoaded(p0: AppOpenAd) {
                            super.onAdLoaded(p0)
                            appOpenAd = p0;
                            isLoadingAd=false
                            loadTime=System.currentTimeMillis();
                            result.success(true)
                            adChannel.invokeMethod("onAdLoaded", null)
                            Log.e(adLog, "AdmobOpenAd==========onAdLoaded")
                        }


                        override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                            super.onAdFailedToLoad(loadAdError)
                            isLoadingAd=false
                            Log.e(adLog, "AdmobOpenAd==========LoadAdError"+loadAdError.message)
                            result.success(false)
                            adChannel.invokeMethod("onAdFailedToLoad", null)
                        }

                    }



                val request = AdRequest.Builder().build()
                AppOpenAd.load(
                    mActivity,
                    adUnitId,
                    request,
                    AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                    loadCallback
                )
            }
            "isLoaded" -> {
                checkExpired()
                var isSuccess = false
                if (appOpenAd != null) {
                    isSuccess = true;
                }
                result.success(isSuccess)
            }
            "show" -> {
                if (isShowingAd) {//正在show
                    return;
                }
                appOpenAd?.setFullScreenContentCallback(object : FullScreenContentCallback() {

                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        appOpenAd = null;
                        isShowingAd = false;
                        result.success(true)
                        Log.e(adLog, "AdmobOpenAd==========onAdDismissedFullScreenContent")
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        super.onAdFailedToShowFullScreenContent(p0)

                        appOpenAd = null;
                        isShowingAd = false;
                        result.success(false)
                        Log.e(adLog, "AdmobOpenAd==========onAdFailedToShowFullScreenContent")
                    }

                    override fun onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent()
                        Log.e(adLog, "AdmobOpenAd==========onAdShowedFullScreenContent")
                    }

                })
                isShowingAd = true;
                appOpenAd?.show(mActivity)
            }

            else -> result.notImplemented()
        }
    }
    /**
     * 检查是否要进行超时清理
     */
    fun checkExpired() {
        if(System.currentTimeMillis()-loadTime>1000*60*60*4){//超时了要进行清理下
            appOpenAd = null;
        }
    }

}
