package geek.me.javaapi.entity.revision;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "node_revision__field_thumb")
public class NodeFieldThumbRevisionEntity {

    @Column(name = "field_thumb_value")
    private String thumb;
    private String bundle = "book";
    @Column(name = "entity_id")
    @Id
    private long bookId;
    private long revision_id;
    private int delta =0;
    private String langcode = "zh-hans";
}
