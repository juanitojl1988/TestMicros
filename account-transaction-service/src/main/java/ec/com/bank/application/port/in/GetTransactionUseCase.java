package ec.com.bank.application.port.in;

import ec.com.bank.domain.model.dto.TransactionDto;
import reactor.core.publisher.Mono;

public interface GetTransactionUseCase {

    Mono<TransactionDto> getById(Long id);
}
