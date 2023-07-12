package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.model.dto.CustomerResDTO;
import com.cg.repository.CustomerRepository;
import com.cg.repository.DepositRepository;
import com.cg.repository.TransferRepository;
import com.cg.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private WithdrawRepository withdrawRepository;
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> findAllByIdNotAndDeletedIsFalse(Long id) {
        return customerRepository.findAllByIdNotAndDeletedIsFalse(id);
    }

    @Override
    public List<CustomerResDTO> findAllCustomerResDTO() {
        return customerRepository.findAllCustomerResDTO();
    }


    @Override
    public Customer deposit(Deposit deposit) {
         deposit.setId(null);
        depositRepository.save(deposit);

        Customer customer = deposit.getCustomer();

        BigDecimal transactionAmount = deposit.getTransactionAmount();

        customerRepository.incrementBalance(customer.getId(), transactionAmount);

        BigDecimal newBalance = customer.getBalance().add(transactionAmount);
        customer.setBalance(newBalance);

        return customer;
    }

    @Override
    public Customer withdraw(Withdraw withdraw) {
        withdraw.setId(null);
        withdrawRepository.save(withdraw);
        Customer customer = withdraw.getCustomer();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        customerRepository.decrementBalance(customer.getId(),transactionAmount);
        BigDecimal newBalance = customer.getBalance().subtract(transactionAmount);
        customer.setBalance(newBalance);


        return customer;

    }

    @Override
    public void incrementBalance(Long id, BigDecimal amount) {
        customerRepository.incrementBalance(id, amount);
    }

    @Override
    public List<Customer> findAllByDeletedIsFalse() {
        return customerRepository.findAllByDeletedIsFalse();
    }

    @Override
    public void decrementBalance(Long id, BigDecimal amount) {
        customerRepository.decrementBalance(id,amount);
    }

    @Override
    public void deleteByIdTrue(Long id) {
        customerRepository.deleteByIdTrue(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByEmailAndIdIsNot(String email, Long id) {
         return customerRepository.existsByEmailAndIdIsNot(email, id);
    }

    @Override
    public Transfer transfer(Transfer transfer) {

        BigDecimal transferAmount = transfer.getTransferAmount();
        BigDecimal transactionAmount = transfer.getTransactionAmount();

        customerRepository.decrementBalance(transfer.getSender().getId(), transactionAmount);

        customerRepository.incrementBalance(transfer.getRecipient().getId(), transferAmount);

        transferRepository.save(transfer);

        Customer sender = customerRepository.findById(transfer.getSender().getId()).get();
        transfer.setSender(sender);

        return transfer;
    }
}