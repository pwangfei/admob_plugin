import 'package:admob_plugin/admob_interstitial.dart';
import 'package:admob_plugin/admob_open_ad.dart';
import 'package:admob_plugin_example/InterstitialAdStandardHelper.dart';
import 'package:admob_plugin_example/OpenAdStandardHelper.dart';
import 'package:admob_plugin_example/interstitial_ad_strategy.dart';
import 'package:admob_plugin_example/interstitial_loading_widget.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:admob_plugin/admob_plugin.dart';
import 'package:get/get.dart';

class HomeView extends StatefulWidget {
  @override
  State<HomeView> createState() => _MyAppState();
}

class _MyAppState extends State<HomeView> {
  String _platformVersion = 'Unknown';
  final _admobPlugin = AdmobPlugin();

  bool isShow = true;
  bool isShowNative = true;

  @override
  void initState() {
    super.initState();
    initPlatformState();


  }

  Future<void> initPlatformState() async {
    String platformVersion;

    try {
      platformVersion =
          await _admobPlugin.getPlatformVersion() ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    var widgetNow = isShow
        ? Container()
        : AndroidView(
            viewType: 'ad_flutter/mix_banner',
            creationParamsCodec: const StandardMessageCodec(),
          );

    var widgetNativeNow = isShowNative
        ? Container()
        : AndroidView(
            viewType: 'ad_flutter/native',
            creationParamsCodec: const StandardMessageCodec(),
          );

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            ElevatedButton(
              onPressed: () {
                InterstitialAdStrategy.showInterstitialAd(() => {


                });
              },
              child: Text('动画加载插页'),
            ),
            ElevatedButton(
              onPressed: () async {
                var success = await OpenAdStandardHelper().load();
                if (success ?? false) {
                  OpenAdStandardHelper().show();
                }
              },
              child: Text('开屏加载'),
            ),

            ElevatedButton(
              onPressed: () {
                setState(() {
                  isShowNative = false;
                });
              },
              child: Text('原生广告'),
            ),
            Container(
              height: 200,
              child: widgetNativeNow,
            ),
            ElevatedButton(
              onPressed: () {
                setState(() {
                  isShow = false;
                });
              },
              child: Text('Banner广告'),
            ),
            Container(
              height: 50,
              child: widgetNow,
            ),
          ],
        ),
      ),
    );
  }
}
