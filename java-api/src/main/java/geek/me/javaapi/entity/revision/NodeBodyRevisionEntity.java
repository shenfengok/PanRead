package geek.me.javaapi.entity.revision;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "node_revision__body")
public class NodeBodyRevisionEntity {
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
    private String langcode ="zh-hans";
    private String body_summary = "";
}
