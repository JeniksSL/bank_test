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

    @Column(name = "contact", nullable = false)
    private ContactType contactType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private User user;

    @Column(name = "user_id", unique = true)
    private Long userId;
}
