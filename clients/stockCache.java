package clients;

import java.util.HashMap;

public class stockCache extends HashMap<String, String> {
    public void bindNameNumber() {
        put("0001", "TV");
        put("0002", "Radio");
        put("0003", "Toaster");
        put("0004", "Watch");
        put("0005", "Camera");
        put("0006", "Music Player");
        put("0007", "USB Drive");
    }

    public String getNumberFromName(HashMap<String, String> map, String name) {
        for (Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        return null;
    }
}