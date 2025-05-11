package ec.com.bank.application.usecases;

import ec.com.bank.application.port.in.ReportTransactionUseCase;
import ec.com.bank.application.port.out.TransactionRepositoryPort;
import ec.com.bank.common.UseCase;
import ec.com.bank.domain.model.dto.PaginatedResponseDto;
import ec.com.bank.domain.model.dto.TransactionWithAccount;
import reactor.core.publisher.Mono;

@UseCase
public class ReportTransactionUseCaseImp implements ReportTransactionUseCase {

    private final TransactionRepositoryPort transactionRepositoryPort;

    public ReportTransactionUseCaseImp(TransactionRepositoryPort transactionRepositoryPort) {
        this.transactionRepositoryPort = transactionRepositoryPort;
    }

    @Override
    public Mono<PaginatedResponseDto<TransactionWithAccount>> finAllTransactionsForDatesAndCustomer(String dateStart, String dateEnd, Long customerId, int page, int size) {
        return transactionRepositoryPort.findAllTransactionsForDatesAndCustomer(dateStart, dateEnd, customerId, page, size);
    }
}
