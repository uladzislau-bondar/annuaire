package properties;


public class DBPropertyService {
    private static PropertyService service;
    private static DBPropertyService instance;


    private DBPropertyService() {
        service = new PropertyService("db");
    }


    public static DBPropertyService getInstance(){
        if (instance == null) {
            instance = new DBPropertyService();
        }
        return instance;
    }


    public String getJDBCDriver() {
        return service.getProperty("jdbc.driver");
    }


    public String getJDBCUrl() {
        return service.getProperty("jdbc.url");
    }

    public String getJDBCUsername(){ return service.getProperty("jdbc.username");}

    public String getJDBCPassword(){return service.getProperty("jdbc.password");}
}
