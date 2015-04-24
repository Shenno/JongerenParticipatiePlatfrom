package com.plusplus.i.jongerenparticipatieplatfrom.model;

import java.util.Date;

/**
 * Created by Shenno on 13/04/2015.
 */
public class DtoDms {
    private int id;
    private String question;
    private String extraInfo;
    private Date endDate;

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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
