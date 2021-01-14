package geek.me.javaapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "queue_new")
public class QueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fsid;
    private Long bookId;
    private String name;
    private String base_path;

    private int todo;
    private Integer finish;
//    private int force;
}
