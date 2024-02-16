package com.ssafy.backend.common.utils;

import java.util.HashMap;

public class MappingUtils {
    private static MappingUtils instance = null;
    private HashMap<String, String> translations;

    private MappingUtils() {
        translations = new HashMap<>();
        translations.put("국어", "Korean");
        translations.put("영어", "English");
        translations.put("수학", "Math");
        translations.put("사회", "Society");
        translations.put("과학","Science");
        translations.put("체육","Athletic");
        translations.put("일본어","Japanese");
        translations.put("중국어","Chinese");
        translations.put("IT","IT");
    }

    public static MappingUtils getInstance() {
        if (instance == null) {
            instance = new MappingUtils();
        }
        return instance;
    }

    public String translate(String subject) {
        if(subject == null) return "SQLD";
        return translations.get(subject);
    }
}