package sforums.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Documented
@Constraint(validatedBy = PasswordRequiredForNewUsersValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordRequiredForNewUsers {
	public abstract String message() default "The password must be set";

	public abstract Class<?>[] groups() default {};

	public abstract Class<?>[] payload() default {};
}