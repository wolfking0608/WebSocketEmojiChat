package com.rance.chatui.bean;

public class BaseResponseEntity {
    private String cmd;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "BaseResponseEntity{" +
                "cmd='" + cmd + '\'' +
                ", code=" + code +
                '}';
    }
}
