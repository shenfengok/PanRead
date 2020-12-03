package rumen.web.baidu.dto;

import geek.me.javaapi.entity.NodeTypeEnum;
import lombok.Data;

@Data
public class PcsItem {
    private int category;
    private long fs_id;
    private int isdir;
    private long local_ctime;
    private long local_mtime;
    private String path;
    private long server_ctime;
    private String server_filename;
    private long server_mtime;
    private int size;

    private NodeTypeEnum parentType;

    public NodeTypeEnum decideType(){
        boolean isDir = isdir == 1;
        switch (parentType) {
            case lib:
                if (isDir) {
                    return NodeTypeEnum.series;
                } else {
                    return NodeTypeEnum.none;
                }
            case series:
            case subSeries:
                if (isDir) {
                    return NodeTypeEnum.subSeries;
                } else {
                    return NodeTypeEnum.seriesItem;
                }
            default:
                return NodeTypeEnum.none;
        }
    }


}
