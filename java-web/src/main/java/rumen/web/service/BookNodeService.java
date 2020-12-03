package rumen.web.service;

import geek.me.javaapi.baidu.PcsApi;
import geek.me.javaapi.baidu.dto.PcsItem;
import geek.me.javaapi.dao.BookNodeDao;
import geek.me.javaapi.dto.view.BookNodeView;
import geek.me.javaapi.entity.BookNodeEntity;
import geek.me.javaapi.entity.NodeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Service
@Deprecated
public class BookNodeService {

//    @Autowired
    private BookNodeDao bookNodeDao;

    @Autowired
    private PcsApi pcsApi;


    public Map<String, BookNodeView> findChilren(String fsId) {
        return null;
    }

    public BookNodeView queryType(String fsId, String name) {
        BookNodeEntity one = queryOne(fsId, name);
        return BookNodeView.from(one);
    }


    private BookNodeEntity queryOne(String fsId, String name) {
        BookNodeEntity one = bookNodeDao.findByFsid(fsId);
        if (null == one) {
            one = new BookNodeEntity();
            one.setFsid(fsId);
            one.setName(name);
            one.setNodeType(NodeTypeEnum.none);
//            BookNodeEntity newOne = bookNodeDao.save(one);
//            one.setId(newOne.getId());
        }

        return one;
    }

    @Transactional
    public BookNodeView markNodeType(String fsId, String name, NodeTypeEnum type) {
        BookNodeEntity node = queryOne(fsId, name);
        BookNodeEntity one = markType(node,type);
        return BookNodeView.from(one);
    }

    /**
     * decideType的类型优先级低，以数据库类型为准,
     * 且decideType只能更新type为none的类型
     * @param node
     * @param type
     * @return
     */
    private BookNodeEntity markType(BookNodeEntity node,NodeTypeEnum type){
        node.setNodeType(type);
//        return bookNodeDao.saveAndFlush(node);
        return null;
    }

    public BookNodeView updateParent(String fsId) {
        return null;
    }

    public BookNodeView syncParent(String fsId) {
        return null;
    }

    /**
     * 查询所有子node 的type
     *
     * @param pId
     * @return
     */
    public List<BookNodeView> queryChildType(String pId, String name) throws InterruptedException {
        NodeTypeEnum parentType;
        List<PcsItem> list = pcsApi.getChildItem(pId);
        BookNodeEntity parent = queryOne(pId, name);
        parentType = parent.getNodeType();
        List<BookNodeView> res = new ArrayList<>();
        for (PcsItem item : list) {
            String fsid = String.valueOf(item.getFs_id());
            item.setParentType(parentType);
            //只能更新为none的
            BookNodeEntity node = queryOne(fsid, item.getServer_filename());
            if(node.getNodeType() == null || node.getNodeType() == NodeTypeEnum.none){
                markType(node,item.decideType());
            }
            updateNode(node,item);
            BookNodeView view = BookNodeView.from(node);
            res.add(view);
        }
        return res;
    }

    /**
     * 使用前使用queryOne确保存在
     */
    private void updateNode(BookNodeEntity node,PcsItem item) {

        node.setFileName(item.getServer_filename());
//        bookNodeDao.saveAndFlush(node);
    }


}
