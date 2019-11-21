package com.example.flutter_gdt_plugin.View;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.flutter_gdt_plugin.FlutterGdtPlugin;
import com.qq.e.ads.banner2.UnifiedBannerADListener;
import com.qq.e.ads.banner2.UnifiedBannerView;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.comm.util.AdError;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.platform.PlatformView;

public class BannerView implements PlatformView, UnifiedBannerADListener {
    String posId;
    PluginRegistry.Registrar mRegistrar;
    Map<String, Object> params;
    UnifiedBannerView mUnifiedBannerView;
    FrameLayout mFrameLayout;
    MethodChannel mMethodChannel;

    public BannerView(PluginRegistry.Registrar registrar, int pos, Map<String, Object> params) {
        this.mRegistrar=registrar;
        this.params =params;
        mMethodChannel=new MethodChannel(registrar.messenger(),"flutter_gdt_plugin/banner"+pos);
        mFrameLayout=new FrameLayout(registrar.activity());
        mFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        getBanner().loadAD();
    }

    @Override
    public View getView() {
        return mFrameLayout;
    }

    @Override
    public void dispose() {

    }



    private UnifiedBannerView getBanner() {
        String posId = (String) params.get("posId");
        if( this.mUnifiedBannerView != null && this.posId.equals(posId)) {
             return this.mUnifiedBannerView;
        }
        this.posId = posId;
        if (this.mUnifiedBannerView != null) {
            this.mUnifiedBannerView.destroy();
            this.mUnifiedBannerView = null;
        }
        this.mUnifiedBannerView = new UnifiedBannerView(mRegistrar.activity(), FlutterGdtPlugin.APPID, posId, this);
        return this.mUnifiedBannerView;
    }

    @Override
    public void onNoAD(AdError adError) {

    }

    @Override
    public void onADReceive() {
        mFrameLayout.removeAllViews();
        mUnifiedBannerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
                mFrameLayout.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels,
                        View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0,
                                View.MeasureSpec.UNSPECIFIED));

                final int targetWidth = mFrameLayout.getMeasuredWidth();
                final int targetHeight = mFrameLayout.getMeasuredHeight();
                HashMap<String, Object> rets = new HashMap<>();

                rets.put("width", targetWidth);
                rets.put("height", targetHeight);
                mMethodChannel.invokeMethod("onADExposure", rets);
            }
        });
        mFrameLayout.addView(mUnifiedBannerView);
        mMethodChannel.invokeMethod("onADReceive", "");
    }

    @Override
    public void onADExposure() {
        HashMap<String, Object> rets = new HashMap<>();

        rets.put("width", mUnifiedBannerView.getWidth());
        rets.put("height", mUnifiedBannerView.getHeight());
        mMethodChannel.invokeMethod("onADExposure", rets);
    }

    @Override
    public void onADClosed() {

    }

    @Override
    public void onADClicked() {

    }

    @Override
    public void onADLeftApplication() {

    }

    @Override
    public void onADOpenOverlay() {

    }

    @Override
    public void onADCloseOverlay() {

    }
}
