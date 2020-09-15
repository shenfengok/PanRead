package geek.me.javaapi.service;

import geek.me.javaapi.baidu.PcsApi;
import geek.me.javaapi.baidu.dto.PcsItem;
import geek.me.javaapi.dao.BookNodeDao;
import geek.me.javaapi.dto.view.BookNodeView;
import geek.me.javaapi.entity.AccountEntity;
import geek.me.javaapi.entity.BookNodeEntity;
import geek.me.javaapi.entity.NodeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BookNodeService {

    @Autowired
    private BookNodeDao bookNodeDao;

    @Autowired
    private PcsApi pcsApi;


    public Map<String, BookNodeView> findChilren(String fsId) {
        return null;
    }

    public BookNodeView queryType(String fsId) {
        BookNodeEntity one = makeOne(fsId);
        return BookNodeView.from(one);
    }

    private BookNodeEntity makeOne(String fsId){
        return makeOne(fsId,NodeTypeEnum.none);
    }

    private BookNodeEntity makeOne(String fsId,NodeTypeEnum type) {
        BookNodeEntity one = bookNodeDao.findByFsid(fsId);
        if (null == one) {
            one = new BookNodeEntity();
            one.setFsid(fsId);

        }
        if(NodeTypeEnum.none == one.getNodeType() || null == one.getNodeType()){
            one.setNodeType(type);
        }
        bookNodeDao.saveAndFlush(one);

        return one;
    }

    public BookNodeView markParentType(String fsId, NodeTypeEnum type) {
        BookNodeEntity one = bookNodeDao.findByFsid(fsId);
        if (null == one) {
            one = new BookNodeEntity();
        }
        one.setFsid(fsId);
        one.setNodeType(type);
        bookNodeDao.saveAndFlush(one);
        return BookNodeView.from(one);
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
    public List<BookNodeView> queryChildType(String pId) throws InterruptedException {
        NodeTypeEnum parentType = NodeTypeEnum.none;
        List<PcsItem> list = pcsApi.getChildItem(pId);
        BookNodeEntity parent = makeOne(pId);
        parentType = parent.getNodeType();
        List<BookNodeView> res = new ArrayList<>();
        for (PcsItem item : list) {
            String fsid = String.valueOf(item.getFs_id());
            item.setParentType(parentType);
            BookNodeEntity node = makeOne(fsid,item.decideType());
            BookNodeView view = BookNodeView.from(node);
            res.add(view);
        }
        return res;
    }


}
