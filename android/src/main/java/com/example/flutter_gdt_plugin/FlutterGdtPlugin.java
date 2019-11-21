package com.example.flutter_gdt_plugin;

import com.example.flutter_gdt_plugin.Factory.FactoryBannerView;
import com.example.flutter_gdt_plugin.Factory.GDTNativeExpress;
import com.qq.e.ads.cfg.MultiProcessFlag;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterGdtPlugin */
public class FlutterGdtPlugin implements MethodCallHandler {
    public static String APPID;

    /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_gdt_plugin");
    channel.setMethodCallHandler(new FlutterGdtPlugin());

    registrar.platformViewRegistry().registerViewFactory("flutter_gdt_plugin/native_list",new GDTNativeExpress(registrar));
    registrar.platformViewRegistry().registerViewFactory("flutter_gdt_plugin/banner",new FactoryBannerView(registrar));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("init")) {
      init(call,result);
    } else {
      result.notImplemented();
    }
  }

    private void init(MethodCall call, Result result) {
        if (call.hasArgument("appId")) {
            MultiProcessFlag.setMultiProcess(true);
            APPID= (String)call.argument("appId");
            result.success("success");
        }else {
            result.error("100", "appId", "");
        }

    }
}
