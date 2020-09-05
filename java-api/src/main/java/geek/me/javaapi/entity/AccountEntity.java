package geek.me.javaapi.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table
public class AccountEntity extends BaseEntity {

    private String userName;
    private String password;
    private String mobile;
    @Id
    @GeneratedValue
    private Long id;

}
