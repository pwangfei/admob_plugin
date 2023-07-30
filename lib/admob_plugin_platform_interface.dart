import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'admob_plugin_method_channel.dart';

abstract class AdmobPluginPlatform extends PlatformInterface {
  /// Constructs a AdmobPluginPlatform.
  AdmobPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static AdmobPluginPlatform _instance = MethodChannelAdmobPlugin();

  /// The default instance of [AdmobPluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelAdmobPlugin].
  static AdmobPluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [AdmobPluginPlatform] when
  /// they register themselves.
  static set instance(AdmobPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
