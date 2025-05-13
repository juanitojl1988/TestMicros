package ec.com.bank.adapter.out.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "tcustomer")
public class CustomerEntity {

    @Id
    @Column("id")
    private Long id;
    @Column("state")
    private String state;
    @Column("password")
    private String password;
}
