package geek.me.javaapi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "queue_new")
public class QueueEntity {
    @Id
    @Column(name = "item_id")
    private long fsid;
    private String name;
    private String base_path;

    private int todo;
//    private int force;
}
