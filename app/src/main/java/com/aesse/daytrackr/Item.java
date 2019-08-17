package com.aesse.daytrackr;

import java.util.Date;
import java.util.UUID;

public class Item {
    private UUID mId;
    private Float mMood;
    private Float mStress;
    private Float mEat;
    private Float mSleep;
    private Float mEx;
    private String mNotes;
    private Date mDate;
    public Item() {
        this(UUID.randomUUID());
    }
    public Item(UUID id) {
        mId = id;
        mDate = new Date();
    }
    public UUID getId() {
        return mId;
    }
    public Float getMood() {
        return mMood;
    }
    public void setMood(Float mood) {
        mMood = mood;
    }
    public Float getStress() {
        return mStress;
    }
    public void setStress(Float stress) {
        mStress = stress;
    }
    public Float getEat() {
        return mEat;
    }
    public void setEat(Float eat) {
        mEat = eat;
    }
    public Float getSleep() {
        return mSleep;
    }
    public void setSleep(Float sleep) {
        mSleep = sleep;
    }
    public Float getEx() {
        return mEx;
    }
    public void setEx(Float ex) {
        mEx = ex;
    }
    public String getNotes() {
        return mNotes;
    }
    public void setNotes(String notes) {
        mNotes = notes;
    }
    public Date getDate() {
        return mDate;
    }
    public void setDate(Date dateCreated) {
        mDate = dateCreated;
    }
}