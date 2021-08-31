package com.chandrakumar.ms.api.customer.resource;

import com.chandrakumar.ms.api.customer.service.CustomerQueryService;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerListResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.chandrakumar.ms.api.customer.resource.CustomerURLConstant.V1_CUSTOMERS_CUSTOMER_ID_URL;
import static com.chandrakumar.ms.api.customer.resource.CustomerURLConstant.V1_CUSTOMER_URL;

@RestController
@Tag(name = "Customer API")
@AllArgsConstructor
public class CustomerQueryRestController {

    @NonNull
    CustomerQueryService customerQueryService;

    @Operation(summary = "Retrieve all customers", description = "Retrieve all customers")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer with the supplied id", content = @Content),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content)
            }
    )
    @GetMapping(V1_CUSTOMER_URL)
    public ResponseEntity<CustomerListResponseDTO> getAllCustomer(
            @RequestParam(required = false) final Integer page,
            @RequestParam(required = false) final Integer size) {

        final CustomerListResponseDTO customerList = customerQueryService.getAllCustomer(
                page,
                size
        );
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping(V1_CUSTOMERS_CUSTOMER_ID_URL)
    @Operation(summary = "Retrieve specific customer with the supplied customer id", description = "Retrieve specific customer with the supplied customer id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved the customer with the supplied id", content = @Content),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content)
            }
    )
    public ResponseEntity<CustomerDTO> getCustomerById(
            @PathVariable(name = CustomerURLConstant.CUSTOMER_ID_PATH) final String customerId) {

        final CustomerDTO customerById = customerQueryService.getCustomerById(
                customerId
        );
        return new ResponseEntity<>(customerById, HttpStatus.OK);
    }
}