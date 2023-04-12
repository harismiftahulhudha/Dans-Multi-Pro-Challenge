package co.harismiftahulhudha.dansmultiprochallenge.core.util.helper;

public class DefaultValueHelper {
    public static String orBlank(String data) {
        if (data != null) {
            return data;
        } else {
            return "";
        }
    }
}
