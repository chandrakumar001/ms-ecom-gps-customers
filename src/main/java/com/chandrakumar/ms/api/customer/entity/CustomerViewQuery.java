package com.chandrakumar.ms.api.customer.entity;

import com.chandrakumar.ms.api.common.audit.Action;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Entity
@Table(name = "customer_view", schema = "customer")
@Data
public class CustomerViewQuery {

    @Id
    private UUID customerId;
    private String emailId;
    @Embedded
    private CustomerName customerName;
    private String age;
    private String favouriteColour;

    @Enumerated(STRING)
    private Action action;
}
