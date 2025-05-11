package ec.com.bank.application.usecases;

import ec.com.bank.application.port.in.CreateAccountUseCase;
import ec.com.bank.application.port.out.AccountRepositoryPort;
import ec.com.bank.application.port.out.ExternalServiceCustomerPort;
import ec.com.bank.common.UseCase;
import ec.com.bank.domain.model.dto.AccountCreateDto;
import ec.com.bank.domain.model.dto.AccountDto;
import ec.com.bank.domain.model.dto.TransactionDto;
import ec.com.bank.domain.model.enums.AccountConstant;
import ec.com.bank.domain.model.enums.TransactionConstant;
import ec.com.bank.domain.model.exception.AccountAlreadyExistsException;
import ec.com.bank.domain.model.util.FunctionUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;



@Slf4j
@UseCase
public class CreateAccountUseCaseImp implements CreateAccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;
    private final ExternalServiceCustomerPort externalServiceCustomerPort;

    public CreateAccountUseCaseImp(AccountRepositoryPort accountRepositoryPort, ExternalServiceCustomerPort externalServiceCustomerPort) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.externalServiceCustomerPort = externalServiceCustomerPort;
    }

    @Override
    public Mono<AccountDto> create(AccountCreateDto accountCreateDto) {
        return accountRepositoryPort.existAccountByNumAccount(accountCreateDto.getNumberAccount()).flatMap(exists -> {
            log.info("Validate ifexist account");
            if (!exists)
                return externalServiceCustomerPort.getCustomerById(accountCreateDto.getCustomerId()).flatMap(customerDto -> {
                    AccountDto accountNewDto = new AccountDto();
                    accountNewDto.setState(AccountConstant.State.A);
                    accountNewDto.setDateCreate(FunctionUtils.now());
                    accountNewDto.setNumberAccount(accountCreateDto.getNumberAccount());
                    accountNewDto.setCustomerId(customerDto.getId());
                    accountNewDto.setTypeAccount(accountCreateDto.getTypeAccount());
                    accountNewDto.setInitialBalance(accountCreateDto.getInitialBalance());
                    //tran
                    TransactionDto transactionNewDto = new TransactionDto();
                    transactionNewDto.setState(AccountConstant.State.A);
                    transactionNewDto.setDateTransaction(FunctionUtils.now());
                    transactionNewDto.setBalance(accountCreateDto.getInitialBalance());
                    transactionNewDto.setValue(accountCreateDto.getInitialBalance());
                    transactionNewDto.setDateFrom(FunctionUtils.now());
                    transactionNewDto.setDateTo(FunctionUtils.maxDate());
                    transactionNewDto.setTypeTransaction(TransactionConstant.TypeTransaction.D);
                    accountNewDto.setTransactionInitDto(transactionNewDto);
                    return accountRepositoryPort.create(accountNewDto);
                }).onErrorResume(e -> {
                    log.error("No se puso completar la creacion, Detalle: {}", e.getMessage());
                    return Mono.error(e);
                });
            else
                return Mono.error(new AccountAlreadyExistsException(accountCreateDto.getNumberAccount()));
        }).doOnError(error -> log.error("Erro general, Detalle: {}", error.getMessage()));
    }
}
