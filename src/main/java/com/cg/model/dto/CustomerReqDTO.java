package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.utils.ValidateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CustomerReqDTO implements Validator {

    private String fullName;
    private String email;
    private String phone;
    private String address;


    public Customer toCustomer(Long id, BigDecimal balance) {
        return new Customer()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setAddress(address)
                .setBalance(balance);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerReqDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerReqDTO customerReqDTO = (CustomerReqDTO) target;

        String fullName = customerReqDTO.fullName;
        String email = customerReqDTO.email;


        if (fullName.length() == 0) {
            errors.rejectValue("fullName", "fullName.empty", "tên không được rỗng" );
        }
        else {
            if (fullName.length() < 5 || fullName.length() > 20) {
                errors.rejectValue("fullName", "fullName.length", "tên từ 5 đến 20 kí tự");
            }
        }

            if (email.length() == 0) {
                errors.rejectValue("email","email.empty","email là bắt buột");
            }else
            if (!ValidateUtils.isEmail(email)){
                errors.rejectValue("email","email.type", "băt buộc phải đúng kiểu email");
            }

    }
}
