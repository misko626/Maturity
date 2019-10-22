package JavaFX.validation;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TextFieldValidation {

    public static boolean isTextFieldNotEmpty(TextField tf) {
        boolean b = false;
        if (tf.getText().length() != 0 || !tf.getText().isEmpty())
            b = true;
        return b;
    }

    public static boolean isTextFieldNotEmpty(TextField tf, Label lb, String errorMessage) {
        boolean b = true;
        String msg = null;
        if (!isTextFieldNotEmpty(tf)) {
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }

    public static boolean isTextFieldTypeNumber(TextField tf) {
        boolean b = false;
        if (tf.getText().matches("([0-9]+(\\.[0-9]+)?)+"))
            b = true;
        return b;
    }

    public static boolean isTextFieldTypeNumber(TextField tf, Label lb, String errorMesage) {
        boolean b = true;
        String msg = null;
        if (!isTextFieldTypeNumber(tf)) {
            b = false;
            msg = errorMesage;
        }
        lb.setText(msg);
        return b;
    }

    public static boolean isTextFieldEmail(TextField tf, Label lb, String errorMesage) {
        boolean bolean = false;
        String msg = null;
        if (tf.getText().matches("(?:[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
            bolean = true;
            msg = errorMesage;
        }
        lb.setText(msg);
        return bolean;
    }
}
