package com.chandrakumar.ms.api.customer.resource;

import com.chandrakumar.ms.api.customer.service.CustomerCommandService;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerBareDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.chandrakumar.ms.api.customer.resource.CustomerURLConstant.*;

@RestController
@Tag(name = "Customer API")
@AllArgsConstructor
//@ConditionalOnProperty(name = "app.write.enabled", havingValue = "true")
public class CustomerCommandRestController {

    @NonNull
    CustomerCommandService customerCommandService;

    @PostMapping(V1_CUSTOMER_URL)
    @Operation(summary = "Create a new customer", description = "Create a new customer")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Created a new customer", content = @Content),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content)
            }
    )
    public ResponseEntity<CustomerDTO> createCustomer(
            @RequestBody @Valid CustomerBareDTO customerBareDTO) {

        final CustomerDTO customerDTO = customerCommandService.createCustomer(
                customerBareDTO
        );
        return new ResponseEntity<>(customerDTO, HttpStatus.CREATED);
    }

    @PutMapping(V1_CUSTOMERS_CUSTOMER_ID_URL)
    @Operation(summary = "Update a customer information", description = "Update a customer information")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Update a customer information", content = @Content),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content)
            }
    )
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable(name = CUSTOMER_ID_PATH) final String customerId,
            @RequestBody @Valid CustomerBareDTO customerBareDTO) {

        final CustomerDTO customerDTO = customerCommandService.updateCustomer(
                customerId,
                customerBareDTO
        );
        return new ResponseEntity<>(customerDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(V1_CUSTOMERS_CUSTOMER_ID_URL)
    @Operation(summary = "Deletes specific Customer with the supplied Customer id", description = "Deletes specific customer with the supplied Customer id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletes specific customer with the supplied Customer id", content = @Content),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Application failed to process the request", content = @Content)
            }
    )
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable(name = CUSTOMER_ID_PATH) final String customerId) {

        customerCommandService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}