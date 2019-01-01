package com.arons.android5779_6274_2436_app2.Model.Backend;

import java.util.regex.Pattern;

public class FieldCheck {

        public static boolean isEmailValid(String email) {
            String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

            Pattern pat = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
            return email != null && pat.matcher(email).matches();
        }

        public static boolean isPhoneNumberValid(String phoneNumber)
        {
            String phoneRegex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

            Pattern pat = Pattern.compile(phoneRegex);
            return phoneNumber != null && pat.matcher(phoneNumber).matches();
        }
        public static boolean isNameValid(String name) {
        String nameRegex = "[A-Z]+\\e+[A-Z]";

        Pattern pat = Pattern.compile(nameRegex,Pattern.CASE_INSENSITIVE);
        return name != null && pat.matcher(name).matches();
    }


}
