package geek.me.javaapi.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;

@Entity
@Data
@Table(indexes = {@Index(name = "myunique",  columnList="fsid",unique = true),
        @Index(name = "my_index_type", columnList="nodeType")})
public class BookNodeEntity extends BaseEntity {

    @NotNull
    private String name;

    private String fsid;
    private String parentFsid;
    private String path;
    private NodeTypeEnum nodeType;
    private String mediaLink;
    private String nodeId;
    private boolean ifFinish;

    /**
     * 0,nothing
     * 1, 内容
     * 2，子专辑
     * 3，专辑
     */
    private int childType;
    /**
     * 0,采集中
     * 1，采集完成
     */
    private int inStatus;

    /**
     * 0,未同步
     * 1，同步完成
     */
    private int outStatus;

    /**
     * 0,未下载
     * 1，下载完成
     * 2，下载失败
     */
    private int fileStatus;
    @Id
    @GeneratedValue
    private Long id;
}
