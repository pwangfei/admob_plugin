import 'package:admob_plugin_example/r_lottie.dart';
import 'package:flutter/material.dart';
import 'package:lottie/lottie.dart';

class InterstitialLoadingWidget extends StatelessWidget {
  const InterstitialLoadingWidget({super.key});

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
        onWillPop: () async {
          return false;
        },
        child: Scaffold(
          backgroundColor: Color(0xFF202E50),
          body: Container(
            width: double.infinity,
            height: double.infinity,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                SizedBox(
                  width: 86,
                  height: 86,
                  child: Lottie.asset(RLottie.loading_ad, animate: true),
                ),
                SizedBox(height: 16,),
                Text("ad_loading", style:  TextStyle(color: Color(0xb3ffffff), fontSize: 16, fontWeight: FontWeight.bold))
              ],
            ),
          ),
        ));
  }
}
