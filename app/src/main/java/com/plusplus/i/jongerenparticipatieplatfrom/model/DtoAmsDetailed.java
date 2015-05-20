package com.plusplus.i.jongerenparticipatieplatfrom.model;

import java.util.Date;

/**
 * Created by Shenno on 20/05/2015.
 */
public class DtoAmsDetailed {
    private int id;
    private String question;
    private String extraInfo;
    private Date startDate;
    private Date endDate;
    private String state;
    private String questioner;
    private String[] tags;
    private String[] rewards;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getQuestioner() {
        return questioner;
    }

    public void setQuestioner(String questioner) {
        this.questioner = questioner;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getRewards() {
        return rewards;
    }

    public void setRewards(String[] rewards) {
        this.rewards = rewards;
    }
}
