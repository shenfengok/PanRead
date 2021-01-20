package geek.me.javaapi.entity.node;

import geek.me.javaapi.util.SqlTextUtil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "node__field_fenlei")
public class BookFieldFenleiEntity {
    @Column(name = "field_fenlei_target_id")
    private int fenleiId;
    private String bundle = "book";
    @Column(name = "entity_id")
    @Id
    private long bookId;
    private long revision_id;
    private int delta =0;
    private String langcode = SqlTextUtil.langCode;
}
