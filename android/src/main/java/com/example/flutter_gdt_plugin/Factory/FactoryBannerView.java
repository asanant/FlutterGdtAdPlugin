package com.example.flutter_gdt_plugin.Factory;

import android.content.Context;

import com.example.flutter_gdt_plugin.View.BannerView;

import java.util.Map;

import io.flutter.plugin.common.MessageCodec;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class FactoryBannerView extends PlatformViewFactory {
    PluginRegistry.Registrar registrar;
    public FactoryBannerView(PluginRegistry.Registrar registrar) {
        super(StandardMessageCodec.INSTANCE);
        this.registrar=registrar;
    }

    @Override
    public PlatformView create(Context context, int i, Object o) {
        Map<String,Object> params=   (Map<String,Object>)o;
        return new BannerView(registrar,i,params);
    }


}
