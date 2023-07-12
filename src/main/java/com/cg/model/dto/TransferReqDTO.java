package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.model.Transfer;
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
public class TransferReqDTO  {


    private Long senderId;
    private Long recipientId;
    private BigDecimal transferAmount;
    private Long fees;
    private BigDecimal feesAmount;
    private BigDecimal transactionAmount;

//    public Transfer toTransfer(Long id, CustomerResDTO sender, CustomerResDTO recipient) {
//        Transfer transfer = new Transfer();
//        transfer.setId(id);
//        transfer.setSender(sender.toCustomer());
//        transfer.setRecipient(recipient.toCustomer());
//        transfer.setFees(fees);
//        transfer.setFeesAmount(feesAmount);
//        transfer.setTransferAmount(transferAmount);
//        transfer.setTransactionAmount(transactionAmount);



//    @Override
//    public boolean supports(Class<?> clazz) {
//        return TransferReqDTO.class.isAssignableFrom(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        TransferReqDTO transferReqDTO = (TransferReqDTO) target;
//
//
//    }
}
