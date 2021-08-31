package com.chandrakumar.ms.api.customer.entity;

import com.chandrakumar.ms.api.common.audit.Action;
import com.chandrakumar.ms.api.common.audit.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "customer", schema = "customer")
@Data
public class Customer extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customerId;
    private String emailId;
    @Embedded
    private CustomerName customerName;
    private String age;
    private String favouriteColour;

    @Enumerated(STRING)
    private Action action;
}
