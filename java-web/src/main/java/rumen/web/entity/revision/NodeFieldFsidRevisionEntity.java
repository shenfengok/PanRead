package rumen.web.entity.revision;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "node_revision__field_fsid")
public class NodeFieldFsidRevisionEntity {

    @Column(name = "field_fsid_value")
    private String fsid;
    private String bundle = "book";
    @Column(name = "entity_id")
    @Id
    private long bookId;
    private long revision_id;
    private int delta =0;
}
