package com.example.login.IdAssigners;

import com.google.gson.Gson;

public class UserIdAssigner {
    public static void main(String[] args) {
        UserIdAssigner test = new UserIdAssigner(1);
        Gson gson = new Gson();
        String json = gson.toJson(test);
        System.out.println(json);
    }
    private static int nextAvaliabltId;
    public UserIdAssigner(Integer start){
        this.nextAvaliabltId = start;
    }
}
