package ec.com.bank;

import ec.com.bank.adapter.in.web.interfaces.AccountController;
import ec.com.bank.application.port.in.CreateAccountUseCase;
import ec.com.bank.domain.model.dto.AccountCreateDto;
import ec.com.bank.domain.model.dto.AccountDto;
import ec.com.bank.domain.model.enums.AccountConstant;
import ec.com.bank.domain.model.exception.AccountAlreadyExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@WebFluxTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockitoBean
    CreateAccountUseCase createAccountUseCase;

    @Test
    @DisplayName("POST /account → 201 Created")
    void createAccountSuccess() {
        var createDto = AccountCreateDto.builder()
                .numberAccount("1234567895")
                .typeAccount(AccountConstant.TypeAccount.A)
                .initialBalance(new BigDecimal(100))
                .state(AccountConstant.State.A)
                .customerId(1L)
                .build();

        var returnedDto = AccountDto.builder()
                .id(10L)
                .numberAccount("1234567895")
                .typeAccount(AccountConstant.TypeAccount.A)
                .initialBalance(new BigDecimal(100))
                .state(AccountConstant.State.A)
                .customerId(1L)
                .build();

        Mockito.when(createAccountUseCase.create(createDto))
                .thenReturn(Mono.just(returnedDto));

        webClient.post()
                .uri("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CREATED)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AccountDto.class)
                .isEqualTo(returnedDto);

        Mockito.verify(createAccountUseCase).create(createDto);
    }

    @Test
    @DisplayName("POST /account → 409 Conflict when account number already exists")
    void createAccountConflict() {
        // 1) Preparamos un DTO de creación válido
        var createDto = AccountCreateDto.builder()
                .numberAccount("ACC123")
                .typeAccount(AccountConstant.TypeAccount.A)
                .initialBalance(new BigDecimal(100))
                .state(AccountConstant.State.A)
                .build();

        Mockito.when(createAccountUseCase.create(createDto))
                .thenReturn(Mono.error(new AccountAlreadyExistsException(createDto.getNumberAccount())));

        webClient.post()
                .uri("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.code").isEqualTo("ACCOUNT_ALREADY_EXISTS")
                .jsonPath("$.message").value(msg ->
                        assertTrue(((String) msg).contains(createDto.getNumberAccount()))
                );
        Mockito.verify(createAccountUseCase).create(createDto);
    }
}
