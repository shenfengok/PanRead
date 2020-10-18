package geek.me.javaapi.dto.form;

import geek.me.javaapi.entity.NodeTypeEnum;
import lombok.Data;

@Data
public class SyncForm {
    private String fsId;
    private NodeTypeEnum type;
    private String name;
    private String basePath;
}
