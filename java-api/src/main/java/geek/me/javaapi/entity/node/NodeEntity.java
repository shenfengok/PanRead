package geek.me.javaapi.entity.node;

import geek.me.javaapi.util.SqlTextUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "node")
public class NodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long nid;

    private String type ="book";
    private String uuid ;
    private long vid =1;
    private String langcode =SqlTextUtil.langCode;
}
