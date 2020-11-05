package rumen.web.entity.node;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "node__body_new")
@Entity
public class NodeBodyEntity {
    @Id
    @Column(name = "entity_id")
    private long nid;
    @Column(name = "body_value")
    private String body;

    @Column(name = "body_summary")
    private String summary ;

    @Column(name = "body_image")
    private String image;

    @Column(name = "body_comment")
    private String comment;

    @Column(name = "body_media")
    private String media;
}
