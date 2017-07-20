package com.pej.pojo;

/**
 * Created by darextossa on 7/20/17.
 */
public class HelperEnum {

    public static String getSituationMatrimoniale(int code){
        switch(code){
            case 1: return "";
            default: return "";
        }
    }

    public static String getSexe(String code){
        switch(code){
            case "1": return "Masculin";
            default: return "Feminin";
        }
    }
}
