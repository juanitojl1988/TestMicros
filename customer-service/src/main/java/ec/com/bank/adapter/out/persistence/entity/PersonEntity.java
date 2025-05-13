package ec.com.bank.adapter.out.persistence.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "tperson")
@Data
public class PersonEntity {

    @Id
    @Column("id")
    private Long id;
    @Column("identification")
    private String identification;
    @Column("name")
    private String name;
    @Column("gender")
    private String gender;
    @Column("age")
    private int age;
    @Column("addresses")
    private String addresses;
    @Column("phone")
    private String phone;




}
