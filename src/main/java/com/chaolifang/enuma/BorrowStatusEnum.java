package com.chaolifang.enuma;

public enum BorrowStatusEnum {

    未出借(1,"未出借"),
    已出借(2,"已出借");

    private Integer index;
    private String name;
    BorrowStatusEnum(Integer index, String name){
        this.index = index;
        this.name = name;
    }

    public static String getNameByIndex(Integer index){
        if (index == null){
            return "";
        }
        for (BorrowStatusEnum item : BorrowStatusEnum.values()){
            if(item.getIndex().equals(index)){
                return item.getName();
            }
        }
        return "";
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
