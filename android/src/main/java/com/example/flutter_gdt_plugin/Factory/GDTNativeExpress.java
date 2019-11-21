package com.example.flutter_gdt_plugin.Factory;

import android.content.Context;

import com.example.flutter_gdt_plugin.View.NativeExpressView;

import java.util.Map;

import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class GDTNativeExpress extends PlatformViewFactory {
    PluginRegistry.Registrar registrar;
    public GDTNativeExpress(PluginRegistry.Registrar registrar) {
        super(StandardMessageCodec.INSTANCE);
        this.registrar=registrar;
    }

    @Override
    public PlatformView create(Context context, int pos, Object args) {
        Map<String,Object> param=(Map<String, Object>) args;
        return new NativeExpressView(this.registrar,pos,param);
    }
}
