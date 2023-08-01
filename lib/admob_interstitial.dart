import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class AdmobInterstitial  {
  static const MethodChannel _channel =
      MethodChannel('admob_flutter/interstitial');

  late int id;
  late MethodChannel _adChannel;
  final String adUnitId;


  AdmobInterstitial({
    required this.adUnitId,
  })  {
    id = hashCode;
    _adChannel = MethodChannel('admob_flutter/interstitial');
    _adChannel.setMethodCallHandler(handleEvent);
  }



  Future<bool?> get isLoaded async {
    final result = await _channel.invokeMethod('isLoaded', _channelMethodsArguments);
    return result;
  }

  void load() async {
    await _channel.invokeMethod('load',
      _channelMethodsArguments
        ..['adUnitId'] = adUnitId
    );
  }

  void show() async {
    if (await isLoaded == true) {
      await _channel.invokeMethod('show', _channelMethodsArguments);
    }
  }

  void dispose() async {
    await _channel.invokeMethod('dispose', _channelMethodsArguments);
  }

  Map<String, dynamic> get _channelMethodsArguments => <String, dynamic>{

  };

  Future<void> handleEvent(MethodCall call) async {

    switch (call.method) {
      case 'loaded':
        print("interstitialAd===================================================loaded");
        break;
      case 'failedToLoad':
        print("interstitialAd===================================================failedToLoad");
        break;
      case 'clicked':
        print("interstitialAd===================================================clicked");
        break;
      case 'impression':
        print("interstitialAd===================================================impression");
        break;
      case 'opened':
        print("interstitialAd===================================================opened");
        break;
      case 'leftApplication':
        print("interstitialAd===================================================leftApplication");
        break;
      case 'closed':
        print("interstitialAd===================================================closed");
        break;
      case 'completed':
        print("interstitialAd===================================================completed");
        break;
      case 'rewarded':
        print("interstitialAd===================================================rewarded");
        break;
      case 'started':

        print("interstitialAd===================================================started");
        break;
    }
  }

}
