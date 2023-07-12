package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.dto.CustomerResDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByIdNotAndDeletedIsFalse(Long id);

    List<Customer> findAllByDeletedIsFalse();

    @Query("select NEW com.cg.model.dto.CustomerResDTO (" +
            "cus.id," +
            "cus.fullName," +
            "cus.email," +
            "cus.phone," +
            "cus.address," +
            "cus.balance" +
            ") FROM Customer AS cus" +
            " WHERE cus.deleted = FALSE ")
    List<CustomerResDTO> findAllCustomerResDTO();
    Boolean existsByEmail(String email);
    boolean existsByEmailAndIdIsNot(String email, Long id);
    @Modifying
    @Query("UPDATE Customer AS c SET c.balance = c.balance + :transactionAmount WHERE c.id = :customerId")
    void incrementBalance(@Param("customerId") Long customerId, @Param("transactionAmount") BigDecimal transactionAmount);

    @Modifying
    @Query("UPDATE Customer AS c SET c.balance = c.balance - :transactionAmount WHERE c.id = :customerId")
    void decrementBalance(@Param("customerId") Long customerId, @Param("transactionAmount") BigDecimal transactionAmount);

    @Modifying
    @Transactional
    @Query("UPDATE Customer c " +
            "SET c.deleted = TRUE " +
            "WHERE c.id = :id")
    void deleteByIdTrue(@Param("id") long id);

}
