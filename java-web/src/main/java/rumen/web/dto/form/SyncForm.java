package rumen.web.dto.form;

import rumen.web.entity.NodeTypeEnum;
import lombok.Data;

@Data
public class SyncForm {
    private String fsId;
    private NodeTypeEnum type;
    private String name;
    private String basePath;
}
