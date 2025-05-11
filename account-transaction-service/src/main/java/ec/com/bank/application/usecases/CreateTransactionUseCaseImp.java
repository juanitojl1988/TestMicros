package ec.com.bank.application.usecases;

import ec.com.bank.adapter.out.persistence.mapper.TransactionDboMapper;
import ec.com.bank.application.port.in.CreateTransactionUseCase;
import ec.com.bank.application.port.out.AccountRepositoryPort;
import ec.com.bank.application.port.out.TransactionRepositoryPort;
import ec.com.bank.common.UseCase;
import ec.com.bank.domain.model.dto.TransactionCreateDto;
import ec.com.bank.domain.model.dto.TransactionDto;
import ec.com.bank.domain.model.enums.AccountConstant;
import ec.com.bank.domain.model.enums.TransactionConstant;
import ec.com.bank.domain.model.exception.AccountInactiveException;
import ec.com.bank.domain.model.exception.AccountNotFoundException;
import ec.com.bank.domain.model.exception.InsufficientBalanceException;
import ec.com.bank.domain.model.exception.NoTransactionsFoundException;
import ec.com.bank.domain.model.util.FunctionUtils;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


@UseCase
public class CreateTransactionUseCaseImp implements CreateTransactionUseCase {

    private final TransactionRepositoryPort transactionRepositoryPort;
    private final AccountRepositoryPort accountRepositoryPort;
    private final TransactionDboMapper transactionDboMapper;

    public CreateTransactionUseCaseImp(TransactionRepositoryPort transactionRepositoryPort, AccountRepositoryPort accountRepositoryPort, TransactionDboMapper transactionDboMapper) {
        this.transactionRepositoryPort = transactionRepositoryPort;
        this.accountRepositoryPort = accountRepositoryPort;
        this.transactionDboMapper = transactionDboMapper;
    }


    @Override
    public Mono<TransactionDto> create(TransactionCreateDto transactionCreateDto) {

        return accountRepositoryPort.findByNumberAccount(transactionCreateDto.getNumberAccount())
                .switchIfEmpty(Mono.error(new AccountNotFoundException(transactionCreateDto.getNumberAccount())))
                .flatMap(accountDto -> {
                    if (!accountDto.getState().equals(AccountConstant.State.A)) {
                        return Mono.error(new AccountInactiveException(accountDto.getNumberAccount()));
                    }
                    return transactionRepositoryPort
                            .findFirstByAccountIdAndDateTo(accountDto.getId())
                            .switchIfEmpty(Mono.error(new NoTransactionsFoundException(accountDto.getId().toString())))
                            .flatMap(transactionLastDto -> {

                                BigDecimal amount      = transactionCreateDto.getAmount();
                                BigDecimal lastBalance = transactionLastDto.getBalance();

                                TransactionConstant.TypeTransaction type =
                                        amount.signum() > 0
                                                ? TransactionConstant.TypeTransaction.D
                                                : TransactionConstant.TypeTransaction.R;

                                BigDecimal newBalance = lastBalance.add(amount);

                                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                                    return Mono.error(new InsufficientBalanceException(accountDto.getId().toString()));
                                }
                                TransactionDto transactionNewDto = new TransactionDto();
                                transactionNewDto.setState(AccountConstant.State.A);
                                transactionNewDto.setDateTransaction(FunctionUtils.now());
                                transactionNewDto.setBalance(newBalance);
                                transactionNewDto.setValue(amount);
                                transactionNewDto.setTypeTransaction(type);
                                transactionNewDto.setDateFrom(FunctionUtils.now());
                                transactionNewDto.setDateTo(FunctionUtils.maxDate());
                                transactionNewDto.setAccountId(transactionLastDto.getAccountId());
                                transactionNewDto.setLastTransaction(transactionLastDto);
                                return transactionRepositoryPort
                                        .create(transactionNewDto)
                                        .map(transactionDto -> transactionDto);

                            });


                });

    }



}
