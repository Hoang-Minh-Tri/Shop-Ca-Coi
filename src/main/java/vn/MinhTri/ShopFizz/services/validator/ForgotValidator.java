package vn.MinhTri.ShopFizz.services.validator;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.MinhTri.ShopFizz.domain.dto.Forgot;
import vn.MinhTri.ShopFizz.services.UserService;

@Service
public class ForgotValidator implements ConstraintValidator<ForgotCheck, Forgot> {
    private final UserService userService;

    public ForgotValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(Forgot user, ConstraintValidatorContext context) {
        boolean valid = true;
        if (!this.userService.CheckEmailExits(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("email không tồn tại").addPropertyNode("email")
                    .addConstraintViolation().disableDefaultConstraintViolation();
            return false;
        }

        if (!this.userService.CheckEmailandFullName(user.getEmail(), user.getFullName())) {
            context.buildConstraintViolationWithTemplate("email và tên không khớp").addPropertyNode("fullName")
                    .addConstraintViolation().disableDefaultConstraintViolation();
            valid = false;
        }
        if (user.getPassword().length() == 0) {
            context.buildConstraintViolationWithTemplate("Vui Lòng Nhập Mật Khẩu").addPropertyNode("password")
                    .addConstraintViolation().disableDefaultConstraintViolation();
            valid = false;
        }
        return valid;
    }
}
