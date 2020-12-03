package rumen.web.baidu.dto;

import lombok.Data;

import java.util.List;

@Data
public class PcsItemView {
    private String fsid;
    private String contentPath;
    private String title;
    private int isdir;
    private String thumb;
    private String comment;
    private String media;
    private String mediaPath;
    private String mediaTitle;
    private String content;

    private int depth;
    private long nid;
    private long vid;

    private List<Long> currentPath;
//    private String basePath;
}
