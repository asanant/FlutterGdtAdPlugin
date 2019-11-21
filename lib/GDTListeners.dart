import 'dart:io';
import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
typedef doubleTwoCallBack =Function (double,double);
class GDTListeners {
  doubleTwoCallBack onADExposure;
  MethodChannel channel;

  GDTListeners({this.onADExposure});

  void bindChannel(String channelName){
    channel=new MethodChannel(channelName);
    channel.setMethodCallHandler(handleMessages);
  }


  Future handleMessages(MethodCall call) {
    switch(call.method){
      case "onADExposure":
        this.onADExposure(double.tryParse(call.arguments["width"].toString()), double.tryParse(call.arguments["height"].toString()));
        break;
    }
  }


}