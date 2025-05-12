package ec.com.bank;

import ec.com.bank.adapter.in.web.CustomerController;
import ec.com.bank.application.port.in.CreateCustomerUseCase;
import ec.com.bank.application.port.in.GetCustomerUseCase;
import ec.com.bank.application.port.in.UpdateCustomerUseCase;
import ec.com.bank.domain.model.dto.CustomerCreateDto;
import ec.com.bank.domain.model.dto.CustomerDto;
import ec.com.bank.domain.model.dto.CustomerUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(CustomerController.class)
public class CustomerControllerTest {


    @Autowired
    private WebTestClient webClient;

    @MockitoBean
    private CreateCustomerUseCase createCustomerUseCase;

    @MockitoBean
    private GetCustomerUseCase getCustomerUseCase;

    @MockitoBean
    UpdateCustomerUseCase updateCustomerUseCase;



    @Test
    @DisplayName("GET /customer/{id} → 200 OK")
    void getByIdFound() {
        CustomerDto dto = new CustomerDto(1L, "123", "Alice", 'F', 30, "Addr", "+1234567", null, 'A');
        Mockito
                .when(getCustomerUseCase.getById(1L))
                .thenReturn(Mono.just(dto));

        webClient.get()
                .uri("/customer/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDto.class)
                .isEqualTo(dto);

        Mockito.verify(getCustomerUseCase).getById(1L);
    }

    @Test
    @DisplayName("GET /customer/{id} → empty Mono → 404 Not Found")
    void getByIdNotFound() {
        Mockito.when(getCustomerUseCase.getById(100L)).thenReturn(Mono.empty());
        webClient.get()
                .uri("/customer/100")
                .exchange()
                .expectStatus().isNotFound();
        Mockito.verify(getCustomerUseCase).getById(100L);
    }

    @Test
    @DisplayName("POST /customer → 201 Created")
    void createCustomer() {
        var create = new CustomerCreateDto("123","Bob",'M',25,"Addr","+1234567","pass1234",'A');
        var saved  = new CustomerDto(2L, "123", "Bob", 'M', 25, "Addr", "+1234567", null, 'A');

        Mockito.when(createCustomerUseCase.create(create))
                .thenReturn(Mono.just(saved));

        webClient.post()
                .uri("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(create)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerDto.class)
                .isEqualTo(saved);

        Mockito.verify(createCustomerUseCase).create(create);
    }

    @Test
    @DisplayName("PUT /customer/{id} → 200 OK")
    void updateCustomer() {
        var update = new CustomerUpdateDto("123","Carol",'F',28,"Addr","+1234567","newpass",'E');
        var updated = new CustomerDto(3L, "123", "Carol", 'F', 28, "Addr", "+1234567", null, 'E');

        Mockito.when(updateCustomerUseCase.update(3L, update))
                .thenReturn(Mono.just(updated));

        webClient.put()
                .uri("/customer/3")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(update)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDto.class)
                .isEqualTo(updated);

        Mockito.verify(updateCustomerUseCase).update(3L, update);
    }

    @Test
    @DisplayName("PUT /customer/{id} → empty Mono → 404 Not Found")
    void updateNotFound() {
        var update = new CustomerUpdateDto("123","Dave",'M',40,"Addr","+1234567","pass1234",'A');
        Mockito.when(updateCustomerUseCase.update(99L, update)).thenReturn(Mono.empty());

        webClient.put()
                .uri("/customer/99")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(update)
                .exchange()
                .expectStatus().isNotFound();

        Mockito.verify(updateCustomerUseCase).update(99L, update);
    }

}


