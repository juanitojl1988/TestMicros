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
    private String gender;
    private int age;
    private String addresses;
    private String phone;
    private String password;
    private String state;

    public CustomerDto() {

    }


}
