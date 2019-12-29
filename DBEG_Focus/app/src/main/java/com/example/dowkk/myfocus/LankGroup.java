package com.example.dowkk.myfocus;

import java.util.ArrayList;

public class LankGroup {
    public ArrayList<String> child;
    public String groupName;
    LankGroup(String name){
        groupName = name;
        child = new ArrayList<String>();
    }
}
