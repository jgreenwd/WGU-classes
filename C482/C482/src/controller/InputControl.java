/**
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C482
 */
package controller;

import java.util.regex.Pattern;
import javafx.scene.control.TextField;


public final class InputControl {
    /* ---------- Validate Integer String Input ---------- */
    private static final Pattern validInteger = Pattern.compile("[0-9]*");
    public static String IntCtrl(String value) {
        if (validInteger.matcher(value).matches()) {
            return value;
        } else {
            return "";
        }
    }
    
    
    /* ---------- Validate Double String Input ---------- */
    private static final Pattern validDouble = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
    // pattern still accepts d & f as valid, since it tests for doubles, not dollars
    public static TextField DblCtrl(TextField field) {
        String value = field.getText();
        try {
            Double.parseDouble(field.getText());
        } catch (NumberFormatException e) {
            field.deleteNextChar();
        }
        
        return field;
    };
}
