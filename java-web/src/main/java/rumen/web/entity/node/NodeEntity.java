package rumen.web.entity.node;

import lombok.Data;

import javax.persistence.*;

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
    private String langcode ="zh-hans";
}
