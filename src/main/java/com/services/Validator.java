package com.services;

public class Validator {
    private static final String  TELEPHONE_REGEX = "^\\+?[0-9]{1,3}[-.\\s]?[0-9]{1,14}$";
    private static final String COORDINATE_REGEX = "^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";
    
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isInvalidCoordinate(String longitude) {
        return Validator.isNullOrEmpty(longitude) && !longitude.matches(COORDINATE_REGEX);
    }
    
    public static boolean isInvalidPhoneNumber(String telephone) {
        return Validator.isNullOrEmpty(telephone) || !(telephone).matches(TELEPHONE_REGEX);
    }
    
}
