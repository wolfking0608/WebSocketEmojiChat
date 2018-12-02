package com.rance.chatui.bean;

import java.util.List;

public class HistoryMsgBean extends BaseResponseEntity{
    private List<HistoryBean> history;

    public List<HistoryBean> getHistory() {
        return history;
    }
    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }
}
