package ec.com.bank.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private Long personId;
    private String identification;
    private String name;
    private Character gender;
    private int age;
    private String addresses;
    private String phone;
}
