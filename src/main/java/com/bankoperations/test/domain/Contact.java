package com.bankoperations.test.domain;


import com.bankoperations.test.dto.core.ContactType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(
        name = "contacts",
        uniqueConstraints = {@UniqueConstraint(name = "contact_type_contact_unique", columnNames = {"contact_type", "contact"})})
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "contact_type", nullable = false)
    private ContactType contactType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;


}
