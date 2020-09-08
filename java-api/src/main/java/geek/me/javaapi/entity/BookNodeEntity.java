package geek.me.javaapi.entity;

import lombok.Data;
import org.springframework.stereotype.Indexed;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Data
@Table(indexes = {@Index(name = "my_index_name",  columnList="name"),
        @Index(name = "my_index_type", columnList="nodeType")})

public class BookNodeEntity extends BaseEntity {

    private String name;
    @Id
    private String fsid;
    private String parentFsid;
    private String path;
    private NodeTypeEnum nodeType;
    private String mediaLink;
    private String nodeId;
    private boolean ifFinish;
}
