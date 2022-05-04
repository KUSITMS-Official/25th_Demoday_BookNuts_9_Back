package team.nine.booknutsbackend.exception;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExceptionResponse {

    public static Object format(Exception e) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("error", e.getClass().getSimpleName());
        map.put("msg", e.getMessage());

        return map;
    }
}