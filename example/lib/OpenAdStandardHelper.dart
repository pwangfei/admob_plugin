import 'package:admob_plugin/admob_interstitial.dart';
import 'package:admob_plugin/admob_open_ad.dart';

class OpenAdStandardHelper {
  static OpenAdStandardHelper? _instance;
  late AdmobOpenAd admobOpenAd;

  factory OpenAdStandardHelper() {
    return _instance ?? OpenAdStandardHelper._internal();
  }

  OpenAdStandardHelper._internal() {
    _instance = this;
    admobOpenAd = AdmobOpenAd(
      adUnitId: 'ca-app-pub-3940256099942544/3419835294',
    );
  }


  Future<bool> isLoaded() async {
    var isReady = await admobOpenAd.isLoaded;
    return isReady ?? false;
  }

  Future<bool> show() async {
    var success = await admobOpenAd.show();
    return success ?? false;
  }

  Future<bool> load() async {
    var success = await admobOpenAd.load();
    return success != null;
  }
}
