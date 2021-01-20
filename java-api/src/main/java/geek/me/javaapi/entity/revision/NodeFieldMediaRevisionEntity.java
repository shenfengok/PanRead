package geek.me.javaapi.entity.revision;

import geek.me.javaapi.util.SqlTextUtil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "node_revision__field_media")
public class NodeFieldMediaRevisionEntity {

    @Column(name = "field_media_value")
    private String media;
    private String bundle = "book";
    @Column(name = "entity_id")
    @Id
    private long bookId;
    private long revision_id;
    private int delta =0;
    private String langcode = SqlTextUtil.langCode;
}
