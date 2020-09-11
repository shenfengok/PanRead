package geek.me.javaapi.dto.view;

import geek.me.javaapi.entity.BookNodeEntity;
import geek.me.javaapi.entity.NodeTypeEnum;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;

@Data
public class BookNodeView {
    public static BookNodeView from(BookNodeEntity one) {
        BookNodeView view = new BookNodeView();
        BeanUtils.copyProperties(one,view);
        if(null == view.getNodeType()){
            view.setNodeType(NodeTypeEnum.none);
        }
        return view;
    }

    private String name;
    @Id
    private String fsid;
    private String parentFsid;
    private String path;
    private NodeTypeEnum nodeType;
    private String mediaLink;
    private String nodeId;
    private boolean ifFinish;

    /**
     * 0,nothing
     * 1, 内容
     * 2，子专辑
     * 3，专辑
     */
    private int childType;
    /**
     * 0,采集中
     * 1，采集完成
     */
    private int inStatus;

    /**
     * 0,未同步
     * 1，同步完成
     */
    private int outStatus;

    /**
     * 0,未下载
     * 1，下载完成
     * 2，下载失败
     */
    private int fileStatus;
}
