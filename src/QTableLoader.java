//import com.google.gson.*;
//import com.google.gson.reflect.TypeToken;
//import java.io.FileReader;
//import java.lang.reflect.Type;
//import java.util.*;
//
//public class QTableLoader {
//  public static Map<String, Map<String, Double>> loadQTable(String filename) {
//    try {
//      Gson gson = new Gson();
//      FileReader reader = new FileReader(filename);
//      Type type = new TypeToken<Map<String, Map<String, Double>>>() {}.getType();
//      return gson.fromJson(reader, type);
//    } catch (Exception e) {
//      e.printStackTrace();
//      return new HashMap<>();
//    }
//  }
//}
