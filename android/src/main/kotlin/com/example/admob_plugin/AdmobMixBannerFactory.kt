package com.example.admob_plugin

import android.app.Activity
import android.content.Context
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

 class AdmobMixBannerFactory(private val messenger: BinaryMessenger,val mActivity: Activity?): PlatformViewFactory(StandardMessageCodec.INSTANCE) {
  override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
    return AdmobMixBanner(context!!, messenger, viewId)
  }
}