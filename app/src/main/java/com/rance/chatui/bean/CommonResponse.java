package com.rance.chatui.bean;

import com.aking.websocket.Response;

public class CommonResponse implements Response<BaseResponseEntity> {

    private String responseText;
    private BaseResponseEntity responseEntity;

    public CommonResponse(String responseText, BaseResponseEntity responseEntity) {
        this.responseText = responseText;
        this.responseEntity = responseEntity;
    }

    @Override
    public String getResponseText() {
        return responseText;
    }

    @Override
    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    @Override
    public BaseResponseEntity getResponseEntity() {
        return this.responseEntity;
    }

    @Override
    public void setResponseEntity(BaseResponseEntity responseEntity) {
        this.responseEntity = responseEntity;
    }
}
