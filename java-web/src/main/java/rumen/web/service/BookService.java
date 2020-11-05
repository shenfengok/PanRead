package rumen.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rumen.web.dao.BookDao;
import rumen.web.dao.NodeBodyDao;
import rumen.web.dao.NodeDao;
import rumen.web.entity.node.BookEntity;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private NodeDao nodeDao;

    @Autowired
    private NodeBodyDao nodeBodyDao;


    public List<BookEntity> listAll() {
        return bookDao.findAll();
    }




    private BookEntity createOneBookEntity(long bookId, int depth, List<Long> path,int hasChild) {
        BookEntity book = bookDao.findByNid(bookId);
        if (null == book) {
            book = new BookEntity();
        }
        book.setNid(bookId);
        if(depth >= 2){
            book.setPid(path.get(depth - 2));
        }else{
            book.setPid(0);
        }

        book.setBid(path.get(0));
        book.setHasChildren(hasChild );
        book.setDepth(depth);
        for (int i = 0; i < path.size(); i++) {
            setN(book, i, path.get(i));
        }
        //更新当前node的nid
        setN(book, depth , book.getNid());
        return bookDao.save(book);
    }



    private void setN(BookEntity be, int n, long value) {
        switch (n) {
            case 1:
                be.setP1(value);
                break;
            case 2:
                be.setP2(value);
                break;
            case 3:
                be.setP3(value);
                break;
            case 4:
                be.setP4(value);
                break;
            case 5:
                be.setP5(value);
                break;
        }
    }
}
