package ec.com.bank.adapter.in.web;

import ec.com.bank.adapter.in.web.interfaces.TransactionController;
import ec.com.bank.application.port.in.CreateTransactionUseCase;
import ec.com.bank.application.port.in.ReportTransactionUseCase;
import ec.com.bank.common.WebAdapter;
import ec.com.bank.domain.model.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@WebAdapter
public class TransactionControllerImp implements TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;
    private final ReportTransactionUseCase reportTransactionUseCase;

    public TransactionControllerImp(CreateTransactionUseCase createTransactionUseCase, ReportTransactionUseCase reportTransactionUseCase) {
        this.createTransactionUseCase = createTransactionUseCase;
        this.reportTransactionUseCase = reportTransactionUseCase;
    }

    @Override
    public Mono<ResponseEntity<TransactionDto>> getTransactionById(Long id) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<TransactionDto>> createTransaction(Mono<TransactionCreateDto> transactionCreateDto) {
        return transactionCreateDto
                .doOnNext(d -> log.info("Payload válido recibido: {}", d))
                .flatMap(dto -> createTransactionUseCase.create(dto)
                        .map(acc -> ResponseEntity.status(HttpStatus.CREATED).body(acc))
                );


    }

    @Override
    public Mono<ResponseEntity<TransactionDto>> updateTransaction(Long id, TransactionUpdateDto transactionUpdateDto) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Void>> removeTransaction(Long id) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<PaginatedResponseDto<TransactionWithAccountDto>>> finAllTransactionsForDatesAndCustomer(String dateStart, String dateEnd, Long customerId, int page, int size) {
        return reportTransactionUseCase.finAllTransactionsForDatesAndCustomer(dateStart, dateEnd, customerId, page, size)
                .map(transactions -> {
                    return new ResponseEntity<>(transactions, HttpStatus.OK);
                });
    }
}
