package ec.com.bank.adapter.out.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;


import ec.com.bank.domain.model.enums.AccountConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "taccount")
public class AccountEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("number_account")
    private String numberAccount;

    @Column("type_account")
    private AccountConstant.TypeAccount typeAccount;

    @Column("initial_balance")
    private BigDecimal initialBalance;

    @Column("state")
    private AccountConstant.State state;

    @Column("customer_id")
    private Long customerId;

    @Column("date_create")
    private Instant dateCreate;

}