package com.rance.chatui.bean;

/**
 * 即时消息
 * 作废
 */
public class IMBean {

    /**
     * cmd : message
     * data : [撇嘴]
     * time : 1543683658
     * type : text
     * user : {"level":"100","name":"seanyou","user_id":"957144"}
     */

    private String cmd;
    private String data;
    private int time;
    private String type;
    private UserBean user;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    public static class UserBean {
        /**
         * level : 100
         * name : seanyou
         * user_id : 957144
         */

        private String level;
        private String name;
        private String user_id;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
