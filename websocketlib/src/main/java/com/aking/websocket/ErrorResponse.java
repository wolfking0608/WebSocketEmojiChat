package com.aking.websocket;

/**
 * 出现错误时的响应
 */
public class ErrorResponse {

    /**
     * 1-WebSocket 未连接或已断开
     * 2-WebSocketService 服务未绑定到当前 Activity/Fragment，或绑定失败
     * 3-WebSocket 初始化未完成
     * 11-数据获取成功，但是解析 JSON 失败
     * 12-数据获取成功，但是服务器返回数据中的code值不正确
     */
    /**
     * cmd : error
     * channal : 2
     * code : 101
     * data : 连接已断开
     */

    private String cmd;
    private int channal;
    private int code;
    private String data;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getChannal() {
        return channal;
    }

    public void setChannal(int channal) {
        this.channal = channal;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
