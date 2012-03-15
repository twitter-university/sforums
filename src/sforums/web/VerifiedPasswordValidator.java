package sforums.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VerifiedPasswordValidator implements
        ConstraintValidator<VerifiedPassword, UserAndPassword> {

    @Override
    public void initialize(VerifiedPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserAndPassword userAndPassword,
            ConstraintValidatorContext context) {
        boolean valid = !userAndPassword.isPasswordSet()
                || userAndPassword.isPasswordVerified();
        if (!valid) {
            // apply the error message to the "passwordVerification" property
            // instead of the entire object
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Passwords do not match").addNode("passwordVerification")
                    .addConstraintViolation();
        }
        return valid;
    }
}