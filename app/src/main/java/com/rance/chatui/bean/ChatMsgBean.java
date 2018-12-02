package com.rance.chatui.bean;

public class ChatMsgBean  extends BaseResponseEntity{

    /**
     * user : {"name":"无昵称","level":"100","user_id":"9705","is_manager":0}
     * time : 1543579460
     * cmd : message
     * channal : 0
     * data : /files/20181130/1543579477.jpg
     * type : image
     */

    private UserBean user;
    private int time;

    private int channal;
    private String data;
    private String type;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public int getChannal() {
        return channal;
    }

    public void setChannal(int channal) {
        this.channal = channal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class UserBean {
        /**
         * name : 无昵称
         * level : 100
         * user_id : 9705
         * is_manager : 0
         */

        private String name;
        private String level;
        private String user_id;
        private int is_manager;

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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public int getIs_manager() {
            return is_manager;
        }

        public void setIs_manager(int is_manager) {
            this.is_manager = is_manager;
        }

        @Override
        public String toString() {
            return "UserBean{" +
                    "name='" + name + '\'' +
                    ", level='" + level + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", is_manager=" + is_manager +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ChatMsgBean{" +
                "user=" + user +
                ", time=" + time +
                ", channal=" + channal +
                ", data='" + data + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
