package vn.MinhTri.ShopFizz.services.validator;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import vn.MinhTri.ShopFizz.domain.dto.DtoRegister;
import vn.MinhTri.ShopFizz.services.UserService;

@Service // Để báo cho sping biết class này có thể kết nối xuống database
public class RegisterValidator implements ConstraintValidator<RegisterCheck, DtoRegister> {
    private final UserService userService;

    public RegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(DtoRegister user, ConstraintValidatorContext context) {
        boolean valid = true;
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Mật khẩu không khớp").addPropertyNode("confirmPassword")
                    .addConstraintViolation().disableDefaultConstraintViolation();
            valid = false;
        }
        if (this.userService.CheckEmailExits(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("email đã tồn tại").addPropertyNode("email")
                    .addConstraintViolation().disableDefaultConstraintViolation();
            valid = false;
        }
        return valid;
    }

}
