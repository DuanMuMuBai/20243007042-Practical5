package com.example.practicalfive;

public class TextProcessor {

    private static final String[] SUPPORTED = {"Beijing", "Shanghai", "Chongqing", "Tianjin"};

    private TextProcessor() {
    }

    public static String processMunicipality(String municipality) {
        if (municipality == null) {
            return null;
        }
        String candidate = municipality.trim();
        if (candidate.isEmpty()) {
            return null;
        }
        for (String name : SUPPORTED) {
            if (name.equalsIgnoreCase(candidate)) {
                return name;
            }
        }
        return null;
    }
}
