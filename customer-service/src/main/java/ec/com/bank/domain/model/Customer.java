package ec.com.bank.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private Long customerid;
    private Character state;
    private String password;
}
