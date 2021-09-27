package com.chandrakumar.ms.api.customer.repository;

import com.chandrakumar.ms.api.customer.entity.CustomerQueryView;
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
public interface CustomerQueryViewRepository extends JpaRepository<CustomerQueryView, UUID> {

    @Override
    @Query("select c from CustomerQueryView c")
    Page<CustomerQueryView> findAll(Pageable customerViewQueryPageable);

    @Query("select c from CustomerQueryView c where c.customerId=:customerId")
    Optional<CustomerQueryView> findByCustomerId(
            @Param(CUSTOMER_ID) @NonNull final UUID customerId
    );
}
