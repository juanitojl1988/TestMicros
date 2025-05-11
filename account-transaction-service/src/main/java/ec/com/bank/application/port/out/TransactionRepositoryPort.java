package ec.com.bank.application.port.out;

import ec.com.bank.domain.model.dto.PaginatedResponseDto;
import ec.com.bank.domain.model.dto.TransactionDto;
import ec.com.bank.domain.model.dto.TransactionWithAccount;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface TransactionRepositoryPort {
    Mono<TransactionDto> getById(Long id);

    Mono<TransactionDto> create(TransactionDto transactionDto);

    Mono<TransactionDto> findFirstByAccountIdAndDateTo(Long idAccount);

    Mono<PaginatedResponseDto<TransactionWithAccount>> findAllTransactionsForDatesAndCustomer(String dateStart, String dateEnd, Long customerId, int page, int size);

}
