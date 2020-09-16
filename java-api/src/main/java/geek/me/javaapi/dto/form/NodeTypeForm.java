package geek.me.javaapi.dto.form;

import geek.me.javaapi.entity.NodeTypeEnum;
import lombok.Data;

@Data
public class NodeTypeForm {
    private String fsId;
    private NodeTypeEnum type;
    private String name;
}
