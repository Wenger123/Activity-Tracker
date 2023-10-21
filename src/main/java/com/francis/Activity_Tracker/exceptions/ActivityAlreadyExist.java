package com.francis.Activity_Tracker.exceptions;

public class ActivityAlreadyExist extends RuntimeException{
    public ActivityAlreadyExist(String message){
        super(message);
    }
}
