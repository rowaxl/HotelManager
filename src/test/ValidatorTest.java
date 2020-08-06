package test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import helper.Validator;

public class ValidatorTest {
    @Test
    @DisplayName("Test validateDateFormat()")
    void testValidateDateFormat() {
        String validDate = "2020-08-07";
        assertTrue(Validator.validateDateFormat(validDate));

        String invalidDate = "2020 Aug 7";
        assertFalse( Validator.validateDateFormat(invalidDate));

        String empty = "";
        assertFalse(Validator.validateDateFormat(empty));

        assertFalse(Validator.validateDateFormat(null));
    }

    @Test
    @DisplayName("Test validateEmailAdd()")
    void testValidateEmailAdd() {
        String validEmail = "this.is@email.com";
        assertTrue(Validator.validateEmailAdd(validEmail));

        String invalidEmail = "this.isNOTemail.com";
        assertFalse(Validator.validateEmailAdd(invalidEmail));

        String empty = "";
        assertFalse(Validator.validateEmailAdd(empty));

        assertFalse(Validator.validateEmailAdd(null));
    }


    @Test
    @DisplayName("Test validatePhoneNum()")
    void testValidatePhoneNum() {
        String validPhoneNumber = "123-456-7890";
        assertTrue(Validator.validatePhoneNum(validPhoneNumber));

        String invalidPhoneNumber = "1-2-3";
        assertFalse(Validator.validatePhoneNum(invalidPhoneNumber));

        String empty = "";
        assertFalse(Validator.validatePhoneNum(empty));

        assertFalse(Validator.validatePhoneNum(null));
    }
}
