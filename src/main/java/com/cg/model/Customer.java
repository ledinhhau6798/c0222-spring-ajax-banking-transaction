package com.cg.model;


import com.cg.model.dto.CustomerDTO;
import com.cg.model.dto.CustomerResDTO;
import com.cg.model.dto.CustomerUpdateResDTO;
import com.cg.utils.ValidateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
@Accessors(chain = true)
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(precision = 10, columnDefinition = "decimal default 0", updatable = false)
    private BigDecimal balance;

    @Column
    private String address;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Deposit> deposits;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Withdraw> withdraws;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Transfer> senders;

    @OneToMany(mappedBy = "recipient")
    @JsonIgnore
    private List<Transfer> recipients;

    public CustomerResDTO toCustomerResDTO() {
        return new CustomerResDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setAddress(address)
                ;
    }

    public CustomerDTO toCustomerDTO() {
        return new CustomerDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setAddress(address)
                ;
    }
    public CustomerUpdateResDTO toCustomerUpdateResDTO() {
        return new CustomerUpdateResDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setAddress(address)
                ;
    }



}
