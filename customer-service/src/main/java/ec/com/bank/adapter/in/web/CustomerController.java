package ec.com.bank.adapter.in.web;

import ec.com.bank.domain.model.dto.CustomerCreateDto;
import ec.com.bank.domain.model.dto.CustomerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "Customers", description = "API for managing customers")
public interface CustomerController {

    @Operation(summary = "Get a customer by its id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("customer/{id}")
    Mono<ResponseEntity<CustomerDto>> getCostumerById(@PathVariable("id") Long id);

    @Operation(summary = "Create a new customer")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("customer")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ResponseEntity<CustomerDto>> createCustomer(@RequestBody @Valid CustomerCreateDto customerCreateDto);
}
