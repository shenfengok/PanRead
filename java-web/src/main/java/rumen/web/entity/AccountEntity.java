package rumen.web.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


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
