package geek.me.javaapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "book_check")
@Data
public class BookCheckEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long item_id;
    private String fsid;
    private String name;
    private int got;
}
