
import 'dart:io';

import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/services.dart';
import 'package:flutter_gdt_plugin/GDTListeners.dart';
import 'package:flutter_gdt_plugin/flutter_gdt_plugin.dart';

class GDTBannerView extends StatefulWidget{
  String posId;
  GDTBannerView({this.posId});
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return StateGDTBannerView();
  }



}

class StateGDTBannerView extends State<GDTBannerView>  with AutomaticKeepAliveClientMixin{
  Size  size;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    size = Size.fromHeight(1);

  }

  @override
  bool get wantKeepAlive => true;
  @override
  Widget build(BuildContext context) {
    super.build(context);
    // TODO: implement build
    if(!FlutterGdtPlugin.isInit){
      return SizedBox();
    }

    if(Platform.isAndroid){
      return Container(
        height: size.height,
        child: AndroidView(
          viewType:"flutter_gdt_plugin/banner",
          creationParamsCodec: const StandardMessageCodec(),
          creationParams: {"posId": widget.posId},
          onPlatformViewCreated: onPlatformViewCreated,
          gestureRecognizers: <Factory<OneSequenceGestureRecognizer>>[
            new Factory<OneSequenceGestureRecognizer>(() => new TapGestureRecognizer()),].toSet(),
        ),
      );

    }else{
      return UiKitView();
    }

  }




  void onPlatformViewCreated(int id) {

    GDTListeners listeners =new GDTListeners(onADExposure:(value){
      setState(() {
        size = value;
      });
    });
    listeners.bindChannel("flutter_gdt_plugin/banner$id");
  }
}