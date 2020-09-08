package geek.me.javaapi.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
public class BaseEntity {

    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date updateDate;

}
