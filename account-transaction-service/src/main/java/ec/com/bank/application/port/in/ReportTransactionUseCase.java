package ec.com.bank.application.port.in;

import ec.com.bank.domain.model.dto.PaginatedResponseDto;
import ec.com.bank.domain.model.dto.TransactionWithAccount;
import reactor.core.publisher.Mono;

public interface ReportTransactionUseCase {

    Mono<PaginatedResponseDto<TransactionWithAccount>> finAllTransactionsForDatesAndCustomer(String dateStart, String dateEnd, Long customerId, int page, int size);

}
