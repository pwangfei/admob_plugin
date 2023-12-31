import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class AdmobInterstitial  {
  static const MethodChannel _channel =
      MethodChannel('admob_flutter/interstitial');


  late MethodChannel _adChannel;
  final String adUnitId;


  AdmobInterstitial({
    required this.adUnitId,
  })  {
    _adChannel = MethodChannel('admob_flutter/interstitial');
    _adChannel.setMethodCallHandler(handleEvent);
  }



  Future<bool?> get isLoaded async {
    final result = await _channel.invokeMethod('isLoaded', _channelMethodsArguments..['adUnitId'] = adUnitId);
    return result;
  }

  Future<bool?> load() async {
   var isSuccess= await _channel.invokeMethod('load',
      _channelMethodsArguments
        ..['adUnitId'] = adUnitId
    );
    return isSuccess;
  }

  Future<bool?> show() async {
      await _channel.invokeMethod('show', _channelMethodsArguments..['adUnitId'] = adUnitId);
  }


  Map<String, dynamic> get _channelMethodsArguments => <String, dynamic>{

  };

  Future<void> handleEvent(MethodCall call) async {

    switch (call.method) {
      case 'loaded':
        print("flutter插页interstitialAd===================================================loaded");
        break;
      case 'failedToLoad':
        print("flutter插页interstitialAd===================================================failedToLoad");
        break;

    }
  }

}
