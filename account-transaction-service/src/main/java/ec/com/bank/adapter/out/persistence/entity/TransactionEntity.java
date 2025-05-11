package ec.com.bank.adapter.out.persistence.entity;

import ec.com.bank.domain.model.enums.AccountConstant;
import ec.com.bank.domain.model.enums.TransactionConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Table(name = "ttransaction")
public class TransactionEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("date_transaction")
    private Instant dateTransaction;

    @Column("type_transaction")
    private TransactionConstant.TypeTransaction typeTransaction;

    @Column("value")
    private BigDecimal value;

    @Column("balance")
    private BigDecimal balance;

    @Column("state")
    private AccountConstant.State state;

    @Column("account_id")
    private Long accountId;

    @Column("date_from")
    private Instant dateFrom;

    @Column("date_to")
    private Instant dateTo;

    @Transient
    private AccountEntity account;

}
