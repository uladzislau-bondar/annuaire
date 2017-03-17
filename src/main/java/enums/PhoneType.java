package enums;


public enum PhoneType {
    HOME("home"),
    MOBILE("mobile"),
    UNKNOWN("unknown");

    private String value;

    PhoneType(String value){
        this.value = value;
    }

    public String value(){
        return value == null ? UNKNOWN.value : value;
    }

}
