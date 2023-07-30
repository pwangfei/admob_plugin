package com.example.admob_plugin

import android.app.Activity
import android.util.Log
import androidx.annotation.NonNull
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler


/** AdmobPlugin */
class AdmobPlugin: FlutterPlugin, MethodCallHandler , ActivityAware {

  private lateinit var channel : MethodChannel
  private lateinit var interstitialChannel: MethodChannel
  private var mActivity: Activity? = null
  private lateinit var flutterPlugin: FlutterPlugin.FlutterPluginBinding;

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    flutterPlugin=flutterPluginBinding
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "admob_plugin")
    channel.setMethodCallHandler(this)
    MobileAds.initialize(flutterPluginBinding.applicationContext, object : OnInitializationCompleteListener {
      override fun onInitializationComplete(p0: InitializationStatus) {

      }

    })


  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
    interstitialChannel.setMethodCallHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    mActivity=binding.activity
    Log.e("wpf123wpf", "onAttachedToActivity================="+mActivity)
    interstitialChannel = MethodChannel(flutterPlugin.binaryMessenger, "admob_flutter/interstitial")
    interstitialChannel.setMethodCallHandler(AdmobInterstitial(flutterPlugin,mActivity))
    interstitialChannel = MethodChannel(flutterPlugin.binaryMessenger, "admob_flutter/apenad")
    interstitialChannel.setMethodCallHandler(AdmobOpenAd(flutterPlugin,mActivity))
    flutterPlugin.platformViewRegistry.apply {
      registerViewFactory("ad_flutter/native", AdmobNativeFactory(flutterPlugin.binaryMessenger))
      registerViewFactory("ad_flutter/mix_banner", AdmobMixBannerFactory(flutterPlugin.binaryMessenger,mActivity)
      )
    }
  }

  override fun onDetachedFromActivityForConfigChanges() {

  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

  }

  override fun onDetachedFromActivity() {

  }


}
