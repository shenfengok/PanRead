package geek.me.javaapi.entity.revision;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "node_revision")
@Entity
public class NodeRevisionEntity {
    private long nid;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long vid;

    private String langcode = "zh-hans";
    private int revision_uid = 1;
    private long revision_timestamp;
    private int revision_default = 1;
}
