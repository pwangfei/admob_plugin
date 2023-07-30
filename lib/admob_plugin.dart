
import 'admob_plugin_platform_interface.dart';

class AdmobPlugin {
  Future<String?> getPlatformVersion() {
    return AdmobPluginPlatform.instance.getPlatformVersion();
  }
}
