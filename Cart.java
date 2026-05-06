import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    public static Map<String, Integer> items = new LinkedHashMap<>();

    // ➕ Add item
    public static void addItem(String item) {
        items.put(item, items.getOrDefault(item, 0) + 1);
    }

    // ➖ Remove one quantity
    public static void removeOne(String item) {
        if (items.containsKey(item)) {
            int qty = items.get(item);
            if (qty > 1) {
                items.put(item, qty - 1);
            } else {
                items.remove(item);
            }
        }
    }

    // ❌ Remove full item
    public static void removeItem(String item) {
        items.remove(item);
    }

    // 📋 Get formatted text
    public static String getCartItems() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            sb.append(entry.getKey())
                    .append(" × ")
                    .append(entry.getValue())
                    .append("\n");
        }

        return sb.toString();
    }

    // 🧹 Clear cart
    public static void clear() {
        items.clear();
    }
}