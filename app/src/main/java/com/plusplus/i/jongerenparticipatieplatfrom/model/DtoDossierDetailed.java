package com.plusplus.i.jongerenparticipatieplatfrom.model;

import java.util.List;

/**
 * Created by Shenno on 24/04/2015.
 */
public class DtoDossierDetailed {
    public int id;
    public String username;
    public String answer;
    public String extra;
    public String location;
    public List<DtoEvent> calendar;
    public List<DtoFixedQuestion> fixedQuestion;
    public String[] photos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<DtoEvent> getCalendar() {
        return calendar;
    }

    public void setCalendar(List<DtoEvent> calendar) {
        this.calendar = calendar;
    }

    public List<DtoFixedQuestion> getFixedQuestion() {
        return fixedQuestion;
    }

    public void setFixedQuestion(List<DtoFixedQuestion> fixedQuestion) {
        this.fixedQuestion = fixedQuestion;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }
}
