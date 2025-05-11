package ec.com.bank.adapter.in.web.interfaces;

import ec.com.bank.domain.model.dto.AccountCreateDto;
import ec.com.bank.domain.model.dto.AccountDto;
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

@Tag(name = "Cuentas", description = "Operaciones CRUD sobre cuentas bancarias")
public interface AccountController {

    @Operation(summary = "Obtener cuenta por ID", description = "Recupera los datos de una cuenta bancaria dado su identificador.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Cuenta encontrada", content = @Content(schema = @Schema(implementation = AccountDto.class))), @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content), @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)})
    @GetMapping("account/{id}")
    Mono<ResponseEntity<AccountDto>> getAccountById(@PathVariable("id") Long id);

    @Operation(summary = "Crear nueva cuenta", description = "Registra una nueva cuenta bancaria con los datos proporcionados.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Cuenta creada correctamente", content = @Content(schema = @Schema(implementation = AccountDto.class))), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content), @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)})
    @PostMapping("account")
    Mono<ResponseEntity<AccountDto>> createAccount(@RequestBody @Valid Mono<AccountCreateDto> accountCreateDtoMono);

    @Operation(summary = "Actualizar cuenta existente", description = "Modifica los datos de una cuenta bancaria existente.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Cuenta actualizada correctamente", content = @Content(schema = @Schema(implementation = AccountDto.class))), @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content), @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content), @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)})
    @PutMapping("account/{id}")
    Mono<ResponseEntity<AccountDto>> updateAccount(@Parameter(description = "Identificador de la cuenta a actualizar", example = "123") @PathVariable("id") Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos para la cuenta", required = true, content = @Content(schema = @Schema(implementation = AccountCreateDto.class))) @RequestBody @Valid AccountCreateDto accountCreateDto);

    @Operation(summary = "Eliminar cuenta", description = "Elimina una cuenta bancaria dado su identificador.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Cuenta eliminada correctamente", content = @Content), @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content), @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)})
    @DeleteMapping("account/{id}")
    Mono<ResponseEntity> removeAccount(@PathVariable("id") Long id);
}