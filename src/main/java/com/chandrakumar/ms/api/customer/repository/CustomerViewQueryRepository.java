package com.chandrakumar.ms.api.customer.repository;

import com.chandrakumar.ms.api.customer.entity.CustomerViewQuery;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.chandrakumar.ms.api.customer.util.CustomerConstant.CUSTOMER_ID;

@Repository
public interface CustomerViewQueryRepository extends JpaRepository<CustomerViewQuery, UUID> {

    @Override
    @Query("select c from CustomerViewQuery c")
    Page<CustomerViewQuery> findAll(Pageable customerViewQueryPageable);

    @Query("select c from CustomerViewQuery c where c.customerId=:customerId")
    Optional<CustomerViewQuery> findByCustomerId(
            @Param(CUSTOMER_ID) @NonNull final UUID customerId
    );
}
