package com.cg.API;

import com.cg.model.Customer;
import com.cg.model.Transfer;
import com.cg.model.dto.TransferReqDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transfers")
public class TransferAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ValidateUtils validateUtils;

    @PostMapping("/{customerId}")
    public ResponseEntity<?> transfer(@PathVariable("customerId") String customerIdStr, @RequestBody TransferReqDTO transferReqDTO) {

        if (!validateUtils.isNumberValid(customerIdStr)) {
            Map<String, String> data = new HashMap<>();
            data.put("message", "Mã khách hàng không hợp lệ");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        Long customerId = Long.valueOf(customerIdStr);
        Optional<Customer> customerOptionalSender = customerService.findById(customerId);
        if (customerOptionalSender.isEmpty()) {
            Map<String, String> data = new HashMap<>();
            data.put("message", "Mã khách hàng chuyển không tồn tại");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }
        if (!validateUtils.isNumberValid(transferReqDTO.getRecipientId().toString())) {
            Map<String, String> data = new HashMap<>();
            data.put("message", "Mã khách hàng nhận tiền không hợp lệ");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }


        Optional<Customer> customerOptionalRecipient = customerService.findById(transferReqDTO.getRecipientId());
        if (customerOptionalRecipient.isEmpty()) {
            Map<String, String> data = new HashMap<>();
            data.put("message", "Mã khách hàng nhận tiền không tồn tại");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        Customer sender = customerOptionalSender.get();
        Customer recipient = customerOptionalRecipient.get();

        BigDecimal newBalanceSender = sender.getBalance().subtract(transferReqDTO.getTransactionAmount());
        BigDecimal newBalanceRecipient = recipient.getBalance().add(transferReqDTO.getTransferAmount());

        if (newBalanceSender.compareTo(BigDecimal.ZERO) < 0) {
             Map<String, String> data = new HashMap<>();
            data.put("message", "số dư không đủ để chuyển tiền");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        sender.setBalance(newBalanceSender);
        Customer newSender = sender;
        customerService.save(newSender);


        recipient.setBalance(newBalanceRecipient);
        Customer newRecipient = recipient;
        customerService.save(recipient);




        return new ResponseEntity<>(HttpStatus.OK);
    }
}
