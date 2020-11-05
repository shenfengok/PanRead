package rumen.web.entity.revision;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "node_field_revision")
@Entity
public class NodeFieldRevisionEntity {
    @Id
    private long nid;
//    private String type = "book";
    private short status =1;
    private long  vid =1;
    private long uid =1;
    private String title;
    private String langcode = "en";
    private long created;
    private long changed;
    private int promote = 0;
    private int sticky = 0;
    private int revision_translation_affected =1;
    private int default_langcode =1;
}
