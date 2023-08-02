import 'package:admob_plugin/admob_interstitial.dart';

class InterstitialAdStandardHelper {
  static InterstitialAdStandardHelper? _instance;
  late AdmobInterstitial interstitialAd;

  factory InterstitialAdStandardHelper() {
    return _instance ?? InterstitialAdStandardHelper._internal();
  }

  InterstitialAdStandardHelper._internal() {
    _instance = this;
    interstitialAd = AdmobInterstitial(
      adUnitId: 'ca-app-pub-3940256099942544/1033173712',
    );
  }


  Future<bool> isLoaded() async {
    var isReady = await interstitialAd.isLoaded;
    return isReady ?? false;
  }

  Future<bool> show() async {
    var success = await interstitialAd.show();
    return success ?? false;
  }

  Future<bool> load() async {
    var success = await interstitialAd.load();
    return success != null;
  }
}
