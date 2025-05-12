package ec.com.bank.domain.model.dto;


import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class PersonDto {

    private Long id;
    private String identification;
    private String name;
    private Character gender;
    private int age;
    private String addresses;
    private String phone;

    public PersonDto(String identification, String name, Character gender, int age, String addresses, String phone) {

        this.identification = identification;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.addresses = addresses;
        this.phone = phone;
    }
}
