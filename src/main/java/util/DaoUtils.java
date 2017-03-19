package util;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class DaoUtils {
//    public static PreparedStatement setEnumOrNull(PreparedStatement statement, Enum value, int pos){
//        if (value == null){
//            statement.setNull(pos, Types.);
//        }
//    }

    public static String fileToPath(File file) {
        return "filePath";
    }

    public static File pathToFile(String path) {
        return new File(path);
    }
}
