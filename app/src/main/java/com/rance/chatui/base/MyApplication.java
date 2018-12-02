package com.rance.chatui.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;
import android.util.DisplayMetrics;

import com.rance.chatui.AppResponseDispatcher;
import com.aking.websocket.WebSocketService;
import com.aking.websocket.WebSocketSetting;

/**
 * 作者：Rance on 2016/12/20 16:49
 * 邮箱：rance935@163.com
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;
    public static Context mContext;
    /**
     * 屏幕宽度
     */
    public static int screenWidth;
    /**
     * 屏幕高度
     */
    public static int screenHeight;
    /**
     * 屏幕密度
     */
    public static float screenDensity;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mInstance = this;
        initScreenSize();
        //配置 WebSocket，必须在 WebSocket 服务启动前设置
        WebSocketSetting.setConnectUrl("wss://chat.xy0test.com:81/echo");//必选
        WebSocketSetting.setResponseProcessDelivery(new AppResponseDispatcher());
        WebSocketSetting.setReconnectWithNetworkChanged(true);
        //启动 WebSocket 服务
        startService(new Intent(this, WebSocketService.class));

        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);
    }

    public static Context getInstance() {
        return mInstance;
    }

    /**
     * 初始化当前设备屏幕宽高
     */
    private void initScreenSize() {
        DisplayMetrics curMetrics = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = curMetrics.widthPixels;
        screenHeight = curMetrics.heightPixels;
        screenDensity = curMetrics.density;
    }
}
