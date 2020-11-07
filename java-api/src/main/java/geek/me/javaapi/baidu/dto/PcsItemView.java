package geek.me.javaapi.baidu.dto;

import lombok.Data;

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
}
