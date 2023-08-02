import 'package:admob_plugin/admob_interstitial.dart';
import 'package:admob_plugin/admob_open_ad.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:admob_plugin/admob_plugin.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _admobPlugin = AdmobPlugin();
  late AdmobInterstitial interstitialAd;
  late AdmobOpenAd admobOpenAd;
  bool isShow = true;
  bool isShowNative = true;
  @override
  void initState() {
    super.initState();
    initPlatformState();
    interstitialAd = AdmobInterstitial(
      adUnitId: 'ca-app-pub-3940256099942544/1033173712',
    );

    admobOpenAd = AdmobOpenAd(
      adUnitId: 'ca-app-pub-3940256099942544/3419835294',
    );
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
              onPressed: () async{
               var success= await interstitialAd.load();
               print("是否加载成功啦====================================${success}");
               if(success??false){
                 interstitialAd.show();
               }
              },
              child: Text('插页加载'),
            ),
            ElevatedButton(
              onPressed: () {
                interstitialAd.show();
              },
              child: Text('展示插页'),
            ),
            ElevatedButton(
              onPressed: () async{
                var success= await  admobOpenAd.load();
                if(success??false){
                  admobOpenAd.show();
                }
              },
              child: Text('开屏加载'),
            ),
            ElevatedButton(
              onPressed: () {
                admobOpenAd.show();
              },
              child: Text('开屏展示'),
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
