package ec.com.bank.application.port.in;

import ec.com.bank.domain.model.dto.TransactionCreateDto;
import ec.com.bank.domain.model.dto.TransactionDto;
import reactor.core.publisher.Mono;

public interface CreateTransactionUseCase {

    Mono<TransactionDto> create(TransactionCreateDto transactionCreateDto);
}
