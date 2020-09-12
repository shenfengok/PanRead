package geek.me.javaapi.service;

import geek.me.javaapi.dao.BookNodeDao;
import geek.me.javaapi.dto.view.BookNodeView;
import geek.me.javaapi.entity.AccountEntity;
import geek.me.javaapi.entity.BookNodeEntity;
import geek.me.javaapi.entity.NodeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookNodeService {

    @Autowired
    private BookNodeDao bookNodeDao;


    public Map<String, BookNodeView> findChilren(String fsId) {
        return null;
    }

    public BookNodeView queryType(String fsId) {
        BookNodeEntity one = makeOne(fsId);
        return BookNodeView.from(one);
    }

    private BookNodeEntity makeOne(String fsId) {
        BookNodeEntity one = bookNodeDao.findByFsid(fsId);
        if(null == one){
            one = new BookNodeEntity();
            one.setFsid(fsId);
            one.setNodeType(NodeTypeEnum.none);
            bookNodeDao.saveAndFlush(one);
        }

        return one;
    }

    public BookNodeView markParentType(String fsId, NodeTypeEnum type) {
        BookNodeEntity one = bookNodeDao.findByFsid(fsId);
        if(null == one){
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
}
