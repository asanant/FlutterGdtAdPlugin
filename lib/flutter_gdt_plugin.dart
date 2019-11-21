import 'dart:async';

import 'package:flutter/services.dart';

class FlutterGdtPlugin {
  static bool isInit=false;
  static const MethodChannel _channel =
      const MethodChannel('flutter_gdt_plugin');

  static Future<String> init({String appId}) async {
    final String result = await _channel.invokeMethod('init',{"appId":appId});
    isInit=true;
    return result;
  }
}
