package com.chandrakumar.ms.api.customer.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class CustomerName implements Serializable {

    private String firstName;
    private String lastName;
}
