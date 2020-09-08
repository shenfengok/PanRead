package geek.me.javaapi.baidu.dto;

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
}
