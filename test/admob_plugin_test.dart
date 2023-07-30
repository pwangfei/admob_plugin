import 'package:flutter_test/flutter_test.dart';
import 'package:admob_plugin/admob_plugin.dart';
import 'package:admob_plugin/admob_plugin_platform_interface.dart';
import 'package:admob_plugin/admob_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockAdmobPluginPlatform
    with MockPlatformInterfaceMixin
    implements AdmobPluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final AdmobPluginPlatform initialPlatform = AdmobPluginPlatform.instance;

  test('$MethodChannelAdmobPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelAdmobPlugin>());
  });

  test('getPlatformVersion', () async {
    AdmobPlugin admobPlugin = AdmobPlugin();
    MockAdmobPluginPlatform fakePlatform = MockAdmobPluginPlatform();
    AdmobPluginPlatform.instance = fakePlatform;

    expect(await admobPlugin.getPlatformVersion(), '42');
  });
}
