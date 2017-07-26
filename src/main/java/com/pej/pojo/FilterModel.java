package com.pej.pojo;

/**
 * Created by darextossa on 7/25/17.
 */
public class FilterModel {

    private String colonne;
    private String operation;
    private String value;

    public FilterModel() {
    }

    public String getColonne() {
        return colonne;
    }

    public void setColonne(String colonne) {
        this.colonne = colonne;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void clean(){
        switch (operation){
            case "equal": this.operation = "="; break;
            case "NoEqual": this.operation = "!=";break;
            case "sup": this.operation = ">";break;
            case "inf": this.operation = "<";break;
            case "supEqual": this.operation = ">=";break;
            case "infEqual": this.operation = "<=";break;
            case "content": this.operation = "like";break;
        }
    }


    public String getQuery(){
        return " o."+this.colonne + ' ' + this.operation + ' '+ "'" + this.value + "'";
    }
}
