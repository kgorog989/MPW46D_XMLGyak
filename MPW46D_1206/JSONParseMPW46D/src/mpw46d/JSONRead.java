package mpw46d;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSONRead {

    public static void main(String[] args) {
        try {
            String jsonFilePath = "MPW46D_1206/JSONParseMPW46D/MPW46D_kurzusfelvetel.json";

            JSONParser parser = new JSONParser();

            // a karakterek megfelelő olvasásához
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(jsonFilePath), "UTF-8"))) {

                Object object = parser.parse(reader);
                JSONObject jsonObject = (JSONObject) object;

                displayJsonObject("", jsonObject);
                
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayJsonObject(String prefix, JSONObject jsonObject) {
        for (Object key : jsonObject.keySet()) {
            String keyStr = (String) key;
            Object value = jsonObject.get(keyStr);

            if (value instanceof JSONObject) {
                System.out.println(prefix + keyStr + ": ");
                displayJsonObject(prefix + "  ", (JSONObject) value);
            } else if (value instanceof JSONArray) {
                System.out.println(prefix + keyStr + ": ");
                displayJsonArray(prefix + "  ", (JSONArray) value);
            } else {
                System.out.println(prefix + keyStr + ": " + value);
            }
        }
    }

    private static void displayJsonArray(String prefix, JSONArray jsonArray) {
        for (Object item : jsonArray) {
            if (item instanceof JSONObject) {
                displayJsonObject(prefix + "  ", (JSONObject) item);
                System.out.println();
            } else if (item instanceof JSONArray) {
                displayJsonArray(prefix + "  ", (JSONArray) item);
            } else {
                System.out.println(prefix + "  " + item);
            }
        }
    }
}
