package com.rance.chatui;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.TypeReference;
import com.rance.chatui.bean.BaseResponseEntity;
import com.rance.chatui.bean.CommonResponse;
import com.aking.websocket.ErrorResponse;
import com.aking.websocket.IResponseDispatcher;
import com.aking.websocket.ResponseDelivery;
import com.rance.chatui.bean.HistoryMsgBean;
import com.rance.chatui.bean.LoginBean;

public class AppResponseDispatcher implements IResponseDispatcher {
    private static final String TAG = "AppResponseDispatcher";


    @Override
    public void onConnected(ResponseDelivery delivery) {
        delivery.onConnected();
    }

    @Override
    public void onConnectError(Throwable cause, ResponseDelivery delivery) {
        delivery.onConnectError(cause);
    }

    @Override
    public void onDisconnected(ResponseDelivery delivery) {
        delivery.onDisconnected();
    }

    /**
     * 统一处理响应的数据
     */
    @Override
    public void onMessageResponse(String message, ResponseDelivery delivery) {
        Log.e(TAG, "onMessageResponse: ---"+message);
        try {
            BaseResponseEntity responseEntity = JSON.parseObject(message, new TypeReference<BaseResponseEntity>() {
            });

       if ("error".equals(responseEntity.getCmd())) {
           ErrorResponse errorResponse = new ErrorResponse();
           errorResponse.setCode(12);
           errorResponse.setData(responseEntity.getCmd());
           onSendMessageError(errorResponse, delivery);

            } else {

           if (message!=null && message.contains("pong")){
              //心跳连接不处理
           }
//           else if(message!=null && message.contains("getHistory")){
//               HistoryMsgBean msgBean = JSON.parseObject(message, new TypeReference<HistoryMsgBean>() {});
//               delivery.onMessageResponse(msgBean);
//           }else if(message!=null && message.contains("login")){
//               LoginBean bean = JSON.parseObject(message, new TypeReference<LoginBean>() {});
//               delivery.onMessageResponse(bean);
//           }
           else{
               delivery.onMessageResponse(message);
           }

            }
        } catch (JSONException e) {
            ErrorResponse errorResponse = new ErrorResponse();
//            errorResponse.setResponseText(message);
            errorResponse.setCode(11);
            errorResponse.setData(e.getMessage());
            onSendMessageError(errorResponse, delivery);
        }
    }

    /**
     * 统一处理错误信息，
     * 界面上可使用 ErrorResponse#getDescription() 来当做提示语
     */
    @Override
    public void onSendMessageError(ErrorResponse error, ResponseDelivery delivery) {
        switch (error.getCode()) {
            case 1://未连接或已断开
                error.setData("网络错误");
                Log.e(TAG, "服务未绑定到当前 Activity/Fragment-----------"+ error.getData());
                break;
            case 2://服务未绑定到当前 Activity/Fragment，或绑定失败
                error.setData("网络错误");
                Log.e(TAG, "服务未绑定到当前 Activity/Fragment-----------"+ error.getData());
                break;
            case 3://初始化未完成
                error.setData("初始化未完成");
                break;
            case 11://数据获取成功，但是解析 JSON 失败
                error.setData("数据格式异常");
                Log.e(TAG, "数据获取成功，但是解析 JSON 失败-----------"+error.getData());
                break;
            case 12://数据获取成功，但是服务器返回数据中的code值不正确
                Log.e(TAG, "数据获取成功，但是服务器返回数据中的code值不正确-----------"+ error.getData());
                break;
            case 101:
                Log.e(TAG, "连接已断开 ----------- "+ error.getData());
                break;
            case 103:
                Log.e(TAG, "连接已断开-----------"+error.getData());
                break;
            case 104:
                Log.e(TAG, "发言太频繁-----------"+ error.getData());
                break;
            case 123:
                Log.e(TAG, "发言太频繁了-----------"+ error.getData());
                break;
        }
        delivery.onSendMessageError(error);
    }


}
