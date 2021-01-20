package geek.me.javaapi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "update_list")
public class UpdateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fsid;
    private Long bookId;
    private String name;
    private String base_path;

}
