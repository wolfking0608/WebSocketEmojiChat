package com.rance.chatui.bean;

public class LoginBean extends BaseResponseEntity {

    /**
     * cmd : login
     * swarm_status : 1
     * is_manager : 0
     * user_status : normal
     * off_time : {start:630,end:0720}
     */


    private String swarm_status;
    private int is_manager;
    private String user_status;
    private String off_time;



    public String getSwarm_status() {
        return swarm_status;
    }

    public void setSwarm_status(String swarm_status) {
        this.swarm_status = swarm_status;
    }

    public int getIs_manager() {
        return is_manager;
    }

    public void setIs_manager(int is_manager) {
        this.is_manager = is_manager;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getOff_time() {
        return off_time;
    }

    public void setOff_time(String off_time) {
        this.off_time = off_time;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "swarm_status='" + swarm_status + '\'' +
                ", is_manager=" + is_manager +
                ", user_status='" + user_status + '\'' +
                ", off_time='" + off_time + '\'' +
                '}';
    }
}
