package com.chandrakumar.ms.api.customer.repository;

import com.chandrakumar.ms.api.customer.entity.Customer;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.chandrakumar.ms.api.customer.util.CustomerConstant.*;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Override
    @Query("select c from Customer c where c.action <> 'DELETED'")
    Page<Customer> findAll(Pageable customerPageable);

    @Query("select c from Customer c where c.emailId=:emailId and c.action <> 'DELETED'")
    Optional<Customer> findByEmailId(
            @Param(EMAIL_ID) @NonNull final String emailId
    );

    @Query("select c from Customer c where c.customerId=:customerId and c.action <> 'DELETED'")
    Optional<Customer> findByCustomerId(
            @Param(CUSTOMER_ID) @NonNull final UUID customerId
    );

    @Query("update Customer c set c.lastModifiedBy=:lastModifiedBy, c.lastModifiedDate=:lastModifiedDate, c.action='DELETED' where c.customerId=:customerId")
    @Modifying
    void softDeleteByCustomerId(
            @Param(CUSTOMER_ID) @NonNull final UUID customerId,
            @Param(LAST_MODIFIED_DATE) @NonNull final Date updatedDate,
            @Param(LAST_MODIFIED_BY) @NonNull final String updatedBy
    );
}
