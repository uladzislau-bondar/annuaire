package enums;

public enum Sex {
    MALE("male"),
    FEMALE("female"),
    UNKNOWN("unknown");

    private String value;

    Sex(String value){
        this.value = value;
    }

    public String value(){
        return value == null ? UNKNOWN.value : value;
    }
}
