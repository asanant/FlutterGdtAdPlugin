package com.example.flutter_gdt_plugin.View;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.flutter_gdt_plugin.FlutterGdtPlugin;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.platform.PlatformView;

public class NativeExpressView implements PlatformView,NativeExpressAD.NativeExpressADListener  {
    PluginRegistry.Registrar mRegistrar;
    Map<String, Object> params;
    NativeExpressAD mNativeExpressAD;
    FrameLayout mFrameLayout;
    MethodChannel mMethodChannel;
    public NativeExpressView(PluginRegistry.Registrar registrar, int pos, Map<String, Object> param) {
        this.mRegistrar=registrar;
        this.params =param;
        mMethodChannel=new MethodChannel(registrar.messenger(),"flutter_gdt_plugin/native_list"+pos);
        mFrameLayout=new FrameLayout(registrar.activity());
        mFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        getExpressAD().loadAD(1);

    }

    @Override
    public View getView() {
        return mFrameLayout;
    }

    @Override
    public void dispose() {
        mNativeExpressAD=null;
        if(mFrameLayout!=null){
            mFrameLayout.removeAllViews();
            mFrameLayout=null;
        }

    }

    private NativeExpressAD getExpressAD() {
        String posId = (String) params.get("posId");
        mNativeExpressAD = new NativeExpressAD(mRegistrar.activity(), new ADSize(ADSize.FULL_WIDTH, ADSize.AUTO_HEIGHT), FlutterGdtPlugin.APPID, posId, this);
        return mNativeExpressAD;
    }

    @Override
    public void onNoAD(AdError adError) {
        mMethodChannel.invokeMethod("onNoAD", adError.getErrorMsg());

        Log.e("onNoAD",adError.getErrorMsg());

    }

    @Override
    public void onADLoaded(List<NativeExpressADView> list) {
        if(list.isEmpty()){
            return;
        }
        NativeExpressADView item =list.get(0);
        item.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
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

        mFrameLayout.removeAllViews();
        mFrameLayout.addView(item);
        item.render();
        mMethodChannel.invokeMethod("onADLoaded", "");
    }

    @Override
    public void onRenderFail(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADExposure(NativeExpressADView nativeExpressADView) {
     /*   HashMap<String, Object> rets = new HashMap<>();
        rets.put("width", nativeExpressADView.getWidth());
        rets.put("height", nativeExpressADView.getHeight());
        mMethodChannel.invokeMethod("onADExposure", rets);*/
    }

    @Override
    public void onADClicked(NativeExpressADView nativeExpressADView) {
        mMethodChannel.invokeMethod("onADClicked", "");

    }

    @Override
    public void onADClosed(NativeExpressADView nativeExpressADView) {
        mMethodChannel.invokeMethod("onADClosed", "");

    }

    @Override
    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

    }
}
