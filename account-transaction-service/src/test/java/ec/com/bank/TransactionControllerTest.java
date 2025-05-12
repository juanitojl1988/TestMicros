package ec.com.bank;

import ec.com.bank.adapter.in.web.interfaces.TransactionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private WebTestClient webClient;



}
