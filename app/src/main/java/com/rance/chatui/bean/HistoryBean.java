package com.rance.chatui.bean;

public class HistoryBean {

    /**
     * type : text
     * user : {"user_id":"1075095","status":"normal","is_manager":0,"name":"分表","level":"0"}
     * time : 1543571710
     * data : 不能换头像吗
     */

    private String type;
    private UserBean user;
    private String time;
    private String data;
    private String cmd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
