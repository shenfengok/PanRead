package rumen.web.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_check")
@Data
public class BookCheckEntity {
    @Id
    @Column(name = "item_id")
    private long id;
    private String fsid;
    private String name;
    private String path;
    private String title;
    private Long vid;
    private int got;
    private int parsed;
    private String parse_fail;
}
