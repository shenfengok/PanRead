package rumen.web.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@Deprecated
public class BaseEntity {

    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date updateDate;

}
