package com.nechet.common.util.requestLogic.Requests;

import java.util.HashMap;

public class AnswerRequests extends BaseRequests<String> {
    private String container;
    public AnswerRequests(String req){
        container=req;
    }
    public String getContainer(){
        return  container;
    }
}