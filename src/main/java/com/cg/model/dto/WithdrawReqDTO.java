package com.cg.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WithdrawReqDTO implements Validator {
    private String transactionAmount;

    @Override
    public boolean supports(Class<?> clazz) {
        return WithdrawReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {


        WithdrawReqDTO withdrawReqDTO = (WithdrawReqDTO) target;

        String transactionAmountStr = withdrawReqDTO.transactionAmount;
        if (transactionAmountStr == null) {
            errors.rejectValue("transactionAmount", "transactionAmount.null", "số tiền cần rút là bắt buộc");
            return;
        }
        if (transactionAmountStr.length() == 0) {
            errors.rejectValue("transactionAmount", "transactionAmount.length", "vui lòng nhập số tiền cần rút");
        } else if (!transactionAmountStr.matches("\\d+")) {
            errors.rejectValue("transactionAmount", "transactionAmount.matches", "Vui lòng nhập giá trị tiền bằng chữ số");
        } else {
            BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(transactionAmountStr));
            if (transactionAmount.compareTo(BigDecimal.valueOf(100L)) <= 0) {
                errors.rejectValue("transactionAmount", "transactionAmount.min", "Số tiền muốn rút thấp nhất là $100");
            } else {
                if (transactionAmount.compareTo(BigDecimal.valueOf(1000000L)) > 0) {
                    errors.rejectValue("transactionAmount", "transactionAmount.max", "Số tiền muốn rút tối đa là $1.000.000");
                }
            }
        }
    }
}
