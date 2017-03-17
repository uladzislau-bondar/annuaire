package enums;

public enum Sex {
    MALE("MALE"),
    FEMALE("FEMALE"),
    UNKNOWN("UNKNOWN");

    private String value;

    Sex(String value){
        this.value = value;
    }

    public String value(){
        return value == null ? UNKNOWN.value : value;
    }
}
