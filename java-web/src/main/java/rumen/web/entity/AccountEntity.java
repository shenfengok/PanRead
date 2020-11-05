package rumen.web.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "users_field_data")
public class AccountEntity extends BaseEntity {
    @Column(name = "name")
    private String userName;
    @Column(name = "pass")
    private String password;
    @Id
    @GeneratedValue
    @Column(name = "uid")
    private Long id;

}
