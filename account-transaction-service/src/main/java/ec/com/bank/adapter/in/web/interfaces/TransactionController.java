package ec.com.bank.adapter.in.web.interfaces;

import ec.com.bank.domain.model.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@Tag(name = "Transacciones", description = "Operaciones CRUD y consultas de transacciones bancarias")
public interface TransactionController {

    @Operation(summary = "Obtener transacción por ID", description = "Recupera los datos de una transacción dado su identificador.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Transacción encontrada", content = @Content(schema = @Schema(implementation = TransactionDto.class))), @ApiResponse(responseCode = "404", description = "Transacción no encontrada", content = @Content), @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)})
    @GetMapping("transaction/{id}")
    Mono<ResponseEntity<TransactionDto>> getTransactionById(@Parameter(description = "Identificador único de la transacción", example = "456") @PathVariable("id") Long id);

    @Operation(summary = "Crear nueva transacción", description = "Registra una nueva transacción con los datos proporcionados.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Transacción creada correctamente", content = @Content(schema = @Schema(implementation = TransactionDto.class))), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content), @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)})
    @PostMapping("transaction")
    Mono<ResponseEntity<TransactionDto>> createTransaction(@RequestBody @Valid Mono<TransactionCreateDto> transactionCreateDto);

    @Operation(summary = "Actualizar transacción existente", description = "Modifica los datos de una transacción existente.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Transacción actualizada correctamente", content = @Content(schema = @Schema(implementation = TransactionDto.class))), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content), @ApiResponse(responseCode = "404", description = "Transacción no encontrada", content = @Content), @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)})
    @PutMapping("transaction/{id}")
    Mono<ResponseEntity<TransactionDto>> updateTransaction(@Parameter(description = "Identificador de la transacción a actualizar", example = "456") @PathVariable("id") Long id, @RequestBody @Valid TransactionUpdateDto transactionUpdateDto);

    @Operation(summary = "Eliminar transacción", description = "Elimina una transacción dado su identificador.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Transacción eliminada correctamente", content = @Content), @ApiResponse(responseCode = "404", description = "Transacción no encontrada", content = @Content), @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)})
    @DeleteMapping("transaction/{id}")
    Mono<ResponseEntity<Void>> removeTransaction(@Parameter(description = "Identificador de la transacción a eliminar", example = "456") @PathVariable("id") Long id);

    @Operation(summary = "Listado de transacciones paginado y por rango de fechas")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Listado de transacciones", content = @Content(schema = @Schema(implementation = TransactionDto.class))), @ApiResponse(responseCode = "400", description = "Parámetros inválidos", content = @Content), @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)})
    @GetMapping("transaction/finAllTransactionsForDatesAndCustomer")
    Mono<ResponseEntity<PaginatedResponseDto<TransactionWithAccountDto>>> finAllTransactionsForDatesAndCustomer(@Parameter(description = "Fecha de inicio (YYYY-MM-DD)", example = "2025-05-01") @RequestParam String dateStart, @Parameter(description = "Fecha de fin (YYYY-MM-DD)", example = "2025-05-31") @RequestParam String dateEnd, @Parameter(description = "Identificador del cliente", example = "789") @RequestParam Long customerId, @Parameter(description = "Número de página (0-based)", example = "0") @RequestParam int page, @Parameter(description = "Tamaño de página", example = "20") @RequestParam int size);


}