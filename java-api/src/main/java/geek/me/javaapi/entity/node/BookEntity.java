package geek.me.javaapi.entity.node;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "book")
public class BookEntity {
    @Id
    private long nid;
    private long bid;
    private long pid;
    @Column(name = "has_children")
    private int hasChildren;
    private long p1;
    private long p2;
    private long p3;
    private long p4;
    private long p5;
    private int weight;
    private int depth;

//    @OneToOne
//    private BookFieldFsid bookFieldFsid;
}
