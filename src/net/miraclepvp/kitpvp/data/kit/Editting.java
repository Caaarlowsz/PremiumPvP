package net.miraclepvp.kitpvp.data.kit;

public enum Editting {

    NAME("name"), DESCRIPTION("description"), ICON("itemcode"), PRICE("price");

    private String name;

    Editting(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
