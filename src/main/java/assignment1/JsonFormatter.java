package assignment1;

import java.util.*;

public class JsonFormatter {
    
    public static String toPrettyJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder();
        toPrettyJsonRecursive(map, json, 0);
        return json.toString();
    }
    
    private static void toPrettyJsonRecursive(Map<String, Object> map, StringBuilder json, int indent) {
        String indentStr = "  ".repeat(indent);
        int i = 0;
        
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.append(indentStr).append("\"").append(entry.getKey()).append("\": ");
            Object value = entry.getValue();
            
            if (value == null) {
                json.append("null");
            } else if (value instanceof String) {
                json.append("\"").append(value).append("\"");
            } else if (value instanceof Number) {
                json.append(value);
            } else if (value instanceof List) {
                appendJsonList((List<?>) value, json, indent);
            } else if (value instanceof Map) {
                json.append("{\n");
                toPrettyJsonRecursive((Map<String, Object>) value, json, indent + 1);
                json.append(indentStr).append("}");
            }
            
            if (i < map.size() - 1) {
                json.append(",");
            }
            json.append("\n");
            i++;
        }
    }
    
    private static void appendJsonList(List<?> list, StringBuilder json, int indent) {
        json.append("[\n");
        String indentStr = "  ".repeat(indent + 1);
        
        for (int j = 0; j < list.size(); j++) {
            Object item = list.get(j);
            json.append(indentStr);
            
            if (item instanceof Map) {
                json.append("{\n");
                toPrettyJsonRecursive((Map<String, Object>) item, json, indent + 2);
                json.append(indentStr).append("}");
            } else {
                json.append(item);
            }
            
            if (j < list.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("  ".repeat(indent)).append("]");
    }
} 