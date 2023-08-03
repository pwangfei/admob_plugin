import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

class AdmobOpenAd  {
  static const MethodChannel _channel =
      MethodChannel('admob_flutter/apenad');

  late MethodChannel _adChannel;
  final String adUnitId;


  AdmobOpenAd({
    required this.adUnitId,
  })  {

    _adChannel = MethodChannel('admob_flutter/apenad');
    _adChannel.setMethodCallHandler(handleEvent);
  }



  Future<bool?> get isLoaded async {
    final result = await _channel.invokeMethod('isLoaded', _channelMethodsArguments);
    return result;
  }

  Future<bool?> load() async {
    var isSuccess=  await _channel.invokeMethod('load',
      _channelMethodsArguments
        ..['adUnitId'] = adUnitId
    );
    return isSuccess;
  }

  Future<bool?> show() async {
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
      case 'onAdLoaded':
        print("开屏===================================================loaded");
        break;
      case 'onAdFailedToLoad':
        print("开屏===================================================failedToLoad");
        break;

    }
  }

}
