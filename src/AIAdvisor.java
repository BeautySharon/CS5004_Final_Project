import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Map;

public class AIAdvisor {
  private final Map<String, Map<String, Double>> qTable;

  public AIAdvisor(String qTablePath) {
    this.qTable = loadQTable(qTablePath);
    System.out.println("✅ Q-table loaded with " + qTable.size() + " entries.");
  }

  public String getOptimalAction(int total, int dealerCard, int usableAce) {
    String key = "(" + total + ", " + dealerCard + ", " + (usableAce == 1 ? "True" : "False") + ")";
    System.out.println("🔍 Looking for key: " + key);

    Map<String, Double> actions = qTable.get(key);
    if (actions == null) {
      return "Unknown";
    }

    return actions.get("hit") > actions.get("stand") ? "Hit" : "Stand";
  }

  private Map<String, Map<String, Double>> loadQTable(String filename) {
    try {
      Gson gson = new Gson();
      FileReader reader = new FileReader(filename);
      Type type = new TypeToken<Map<String, Map<String, Double>>>() {}.getType();
      return gson.fromJson(reader, type);
    } catch (Exception e) {
      e.printStackTrace();
      return Map.of();  // empty map fallback
    }
  }
}
