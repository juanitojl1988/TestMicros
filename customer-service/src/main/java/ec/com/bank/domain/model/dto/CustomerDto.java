package ec.com.bank.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CustomerDto {
    private Long id;
    private String identification;
    private String name;
    private Character gender;
    private int age;
    private String addresses;
    private String phone;
    private String password;
    private Character state;

    public CustomerDto() {

    }
}
