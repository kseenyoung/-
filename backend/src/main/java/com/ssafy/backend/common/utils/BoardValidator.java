package com.ssafy.backend.common.utils;

import com.ssafy.backend.common.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class BoardValidator {
    private static final String TITLE_PATTERN = "^(?!.*<script>)(?=.{1,39}$)(?!\\s*$).+";
    private static final String CONTENT_PATTERN = "^(?!.*<script>)(?=.{1,1999}$)(?!\\s*$).+";

    public static void nullCheck(String data) {
        if(data == null){
            throw new MyException("null Error", HttpStatus.BAD_REQUEST);
        }
    }
    public static boolean isValidTitle(String title) throws MyException {
        nullCheck(title);
        return title != null && title.matches(TITLE_PATTERN);
    }

    public static boolean isValidContent(String content) throws MyException {
        nullCheck(content);
        return content != null && content.matches(CONTENT_PATTERN);
    }

    public static boolean isNumeric(String str) {
        if (StringUtils.hasText(str)) {
            for (char c : str.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
