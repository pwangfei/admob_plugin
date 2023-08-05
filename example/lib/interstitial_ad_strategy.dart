import 'package:admob_plugin_example/InterstitialAdStandardHelper.dart';
import 'package:admob_plugin_example/function.dart';
import 'package:admob_plugin_example/interstitial_loading_widget.dart';
import 'package:get/get.dart';

///插页加载机制
class InterstitialAdStrategy {
  static void loadAd() async {}

  static Future<void> showInterstitialAd(ParamVoidCallback afterAd) async {
    //1.有广告直接展示
    var isReady = await InterstitialAdStandardHelper().isLoaded();
    print("当前插页是否正确缓存===============================================${isReady}");
    if (isReady) {
      await InterstitialAdStandardHelper().show();
      afterAd.call();
      return;
    }

    //2.打开loading页
    Get.dialog(const InterstitialLoadingWidget());

    //3.等待
    await delayShowInterstitialAd();

    //4.关闭loading页
    if (Get.isDialogOpen != null && Get.isDialogOpen!) {
      Get.back();
    }

    await InterstitialAdStandardHelper().show();
    afterAd.call();
  }

  static Future<void> delayShowInterstitialAd() async {
    int waitTime = 0;
    int needWaitTime = 12000;
    InterstitialAdStandardHelper().load();///todo 缓存机制
    while (waitTime < needWaitTime) {
      var isReady = await InterstitialAdStandardHelper().isLoaded();
      if (isReady) {
        return;
      }
      await Future.delayed(const Duration(milliseconds: 100));
      waitTime += 100;
    }
  }
}
