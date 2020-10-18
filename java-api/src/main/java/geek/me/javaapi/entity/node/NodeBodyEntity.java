package geek.me.javaapi.entity.node;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "node__body")
@Entity
public class NodeBodyEntity {
    @Id
    @Column(name = "entity_id")
    private long nid;
    @Column(name = "body_value")
    private String body;
    @Column(name = "body_format")
    private String format ="full_html";
    private int delta=0;
    private long revision_id;
    private String bundle = "book";
}
