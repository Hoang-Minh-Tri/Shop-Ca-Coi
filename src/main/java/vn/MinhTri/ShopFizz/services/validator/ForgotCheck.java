package vn.MinhTri.ShopFizz.services.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ForgotValidator.class)
@Target({ ElementType.TYPE }) // Phạm vi trong 1 class)
@Retention(RetentionPolicy.RUNTIME) // Khi nào thì chạy
public @interface ForgotCheck {
    String message() default "Thông tin không khớp";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
