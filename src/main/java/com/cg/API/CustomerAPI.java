package com.cg.API;

import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.model.Customer;
import com.cg.model.dto.CustomerDTO;
import com.cg.model.dto.CustomerReqDTO;
import com.cg.model.dto.CustomerResDTO;
import com.cg.model.dto.CustomerUpdateReqDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ValidateUtils validateUtils;

    @GetMapping
    public ResponseEntity<?> getAll(){

        List<CustomerResDTO> customerResDTOS = customerService.findAllCustomerResDTO();

        return new ResponseEntity<>(customerResDTOS,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CustomerReqDTO customerReqDTO, BindingResult bindingResult) {

        new CustomerReqDTO().validate(customerReqDTO,bindingResult);
        if (bindingResult.hasFieldErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Customer customer = customerReqDTO.toCustomer(null,BigDecimal.ZERO);
        customerService.save(customer);
        CustomerResDTO customerResDTO = customer.toCustomerResDTO();
        return new ResponseEntity<>(customerResDTO, HttpStatus.OK);

    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> findById(@PathVariable String customerId){
        if (!validateUtils.isNumberValid(customerId)){
            throw new DataInputException("Mã khách hàng không hợp lệ");
        }
        Long id = Long.valueOf(customerId);

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

         CustomerDTO customerDTO = customerOptional.get().toCustomerDTO();

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PatchMapping("/edit/{customerId}")
    public ResponseEntity<?> update(@PathVariable String customerId, @RequestBody CustomerUpdateReqDTO customerUpdateReqDTO,BindingResult bindingResult){
        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách hàng không hợp lệ");
        }

        new CustomerUpdateReqDTO().validate(customerUpdateReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Long id = Long.parseLong(customerId);

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Mã khách hàng không tồn tại");
        }

        Boolean existEmail = customerService.existsByEmailAndIdIsNot(customerUpdateReqDTO.getEmail(), id);

        if (existEmail) {
            throw new EmailExistsException("Email đã tồn tại");
        }

        Customer customer = customerUpdateReqDTO.toCustomer();
        Customer customerUpdate = customerOptional.get();

        customer.setId(customerUpdate.getId());
        customer.setBalance(customerOptional.get().getBalance());

        customerService.save(customer);


        return new ResponseEntity<>(customer.toCustomerUpdateResDTO(), HttpStatus.OK);
    }

    @PatchMapping("/delete/{customerId}")
    public ResponseEntity<?> deleted(@PathVariable String customerId ){
        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách hàng không hợp lệ");
        }

        customerService.deleteByIdTrue(Long.valueOf(customerId));

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
