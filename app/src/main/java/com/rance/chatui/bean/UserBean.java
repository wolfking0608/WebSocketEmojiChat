package com.rance.chatui.bean;

public class UserBean {
    private String user_id;
    private String status;
    private int is_manager;
    private String name;
    private String level;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIs_manager() {
        return is_manager;
    }

    public void setIs_manager(int is_manager) {
        this.is_manager = is_manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "user_id='" + user_id + '\'' +
                ", status='" + status + '\'' +
                ", is_manager=" + is_manager +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
