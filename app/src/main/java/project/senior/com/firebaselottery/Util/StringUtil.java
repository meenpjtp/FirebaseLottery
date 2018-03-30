package project.senior.com.firebaselottery.Util;

public abstract class StringUtil {

    public static boolean isEmpty(String paramString) {
        if (paramString == null) {
            return true;
        }
        if (paramString.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isLength(String paramString){
        if(paramString.length() != 6){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String paramString) {
        if (paramString != null && !paramString.isEmpty()) {
            return true;
        }
        return false;
    }

}
