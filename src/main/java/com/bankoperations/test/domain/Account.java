package com.bankoperations.test.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "initial_deposit", nullable = false)
    private BigDecimal initialDeposit;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private User user;

    @Column(name = "user_id", unique = true)
    private Long userId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    List<Contact> contacts;











}
