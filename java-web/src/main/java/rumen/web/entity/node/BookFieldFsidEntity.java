package rumen.web.entity.node;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "node__field_fsid")
public class BookFieldFsidEntity {
    @Column(name = "field_fsid_value")
    private String fsid;
    @Column(name = "entity_id")
    @Id
    private long bookId;
}
