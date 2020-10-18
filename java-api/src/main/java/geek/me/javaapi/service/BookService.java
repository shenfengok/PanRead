package geek.me.javaapi.service;

import geek.me.javaapi.baidu.PcsApi;
import geek.me.javaapi.baidu.dto.PcsItem;
import geek.me.javaapi.baidu.dto.PcsItemView;
import geek.me.javaapi.dao.*;
import geek.me.javaapi.dto.form.SyncForm;
import geek.me.javaapi.entity.node.*;
import geek.me.javaapi.entity.revision.NodeBodyRevisionEntity;
import geek.me.javaapi.entity.revision.NodeFieldFsidRevisionEntity;
import geek.me.javaapi.entity.revision.NodeFieldRevisionEntity;
import geek.me.javaapi.entity.revision.NodeRevisionEntity;
import org.apache.tomcat.util.ExceptionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private NodeDao nodeDao;

    @Autowired
    private BookFieldFsidDao bookFieldFsidDao;

    @Autowired
    private PcsApi pcsApi;

    @Autowired
    private NodeBodyDao nodeBodyDao;

    @Autowired
    private NodeFieldDataDao nodeFieldDataDao;

    @Autowired
    private NodeVersionDao nodeVersionDao;

    @Autowired
    private NodeFieldRevisionDao nodeFieldRevisionDao;

    @Autowired
    private NodeFiledFsidRevisionDao nodeFiledFsidRevisionDao;

    @Autowired
    private NodeBodyRevisionDao nodeBodyRevisionDao;



    public List<BookEntity> listAll() {
        return bookDao.findAll();
    }

    public boolean sync(SyncForm form) throws InterruptedException {

        List<Long> path = new ArrayList<>();
        NodeEntity aBook = createRoot(form.getFsId(),form.getName(),1,path,1, form.getBasePath(),"");


        return true;
    }



    /**
     * 创建一本书
     *
     * @param fsid
     * @return
     */
    private NodeEntity createRoot(String fsid,String title,int depth,List<Long> parentPath,int hasChild,String basePath,String content) throws InterruptedException {

        NodeEntity node1 = creatOneNode(fsid);

        long nid = node1.getNid();
        long vid = node1.getVid();
        createOneFsidEntity(fsid, nid,vid);

        List<Long> newPath = new ArrayList<>();
        newPath.addAll(parentPath);
        newPath.add(nid);
        createOneBookEntity(nid, depth, newPath,hasChild);


        createOneNodeBody(nid,content,vid);

        createOneFieldData(nid,title,vid);


        if(hasChild == 1){
            List<PcsItemView> list = pcsApi.getChildItemView(fsid,basePath);
            for (PcsItemView item : list) {
                String newCon = getCon(item);
                createRoot(String.valueOf(item.getFsid()),item.getTitle(),depth + 1,newPath,item.getIsdir(),basePath,newCon);
            }
        }

        return node1;
    }

    private String getCon(PcsItemView item) {
        if(item.getIsdir() == 1){
            return "";
        }

        try{
            String path = "Z:\\pan\\zhuanlan\\" + item.getContentPath();

            File htmlf=new File(path);
            Document doc= Jsoup.parse(htmlf, "UTF-8");
            Elements els =doc.getElementsByTag("img");
            els.forEach(x->{
                String imgLink = x.attr("data-savepage-src");
                x.attr("src",imgLink);
                x.removeAttr("data-savepage-src");
            });
            String con =  doc.getElementsByClass("_29HP61GA_0").get(0).html();
            return con;
        }catch (Exception e){
            System.out.println(e);
        }
        return "";
    }

    private NodeFiledDataEntity createOneFieldData(long nid, String title, long vid) {
        NodeFiledDataEntity  nodeFiledDataEntity = nodeFieldDataDao.findByNid(nid);
        if(null == nodeFiledDataEntity){
            nodeFiledDataEntity = new NodeFiledDataEntity();
        }
        nodeFiledDataEntity.setNid(nid);
        nodeFiledDataEntity.setTitle(title);
        nodeFiledDataEntity.setVid(vid);
        long time = new Date().getTime()/ 1000;
        nodeFiledDataEntity.setCreated(time);
        nodeFiledDataEntity.setChanged(time);

        //revision
        NodeFieldRevisionEntity nodeFieldRevisionEntity = new NodeFieldRevisionEntity();
        nodeFieldRevisionEntity.setNid(nid);
        nodeFieldRevisionEntity.setTitle(title);
        nodeFieldRevisionEntity.setVid(vid);
        nodeFieldRevisionEntity.setCreated(time);
        nodeFieldRevisionEntity.setChanged(time);
        nodeFieldRevisionDao.save(nodeFieldRevisionEntity);

        return nodeFieldDataDao.save(nodeFiledDataEntity);
    }

    /**
     * 内容entity
     * @param nid
     * @param content
     */
    private NodeBodyEntity createOneNodeBody(long nid, String content, long vid) {
        NodeBodyEntity nodeBodyEntity = nodeBodyDao.findByNid(nid);
        if(null == nodeBodyEntity){
            nodeBodyEntity = new NodeBodyEntity();
        }
        nodeBodyEntity.setBody(content);
        nodeBodyEntity.setNid(nid);
        nodeBodyEntity.setRevision_id(vid);

        //revision
        NodeBodyRevisionEntity nodeBodyRevisionEntity = new NodeBodyRevisionEntity();
        nodeBodyRevisionEntity.setBody(content);
        nodeBodyRevisionEntity.setNid(nid);
        nodeBodyRevisionEntity.setRevision_id(vid);
        nodeBodyRevisionDao.save(nodeBodyRevisionEntity);

        return nodeBodyDao.save(nodeBodyEntity);
    }

    private BookFieldFsidEntity createOneFsidEntity(String fsid, long nid, long vid) {
        BookFieldFsidEntity bookFieldFsidEntity = new BookFieldFsidEntity();
        bookFieldFsidEntity.setBookId(nid);
        bookFieldFsidEntity.setFsid(fsid);
        bookFieldFsidEntity.setRevision_id(vid);

        BookFieldFsidEntity result =  bookFieldFsidDao.save(bookFieldFsidEntity);

        //revision
        NodeFieldFsidRevisionEntity nodeFieldFsidRevisionEntity = new NodeFieldFsidRevisionEntity();
        nodeFieldFsidRevisionEntity.setBookId(nid);
        nodeFieldFsidRevisionEntity.setFsid(fsid);
        nodeFieldFsidRevisionEntity.setRevision_id(vid);
        nodeFiledFsidRevisionDao.save(nodeFieldFsidRevisionEntity);


        return result;
    }

    private NodeEntity creatOneNode(String fsid) {
        BookFieldFsidEntity bookFieldFsid = bookFieldFsidDao.findByFsid(fsid);
        if (null != bookFieldFsid) {
            NodeEntity nodeEntity = nodeDao.findByNid(bookFieldFsid.getBookId());
            return nodeEntity;
        }
        //revision
        NodeRevisionEntity nodeVersion = createOneVersion();
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setUuid(UUID.randomUUID().toString());
        nodeEntity.setVid(nodeVersion.getVid());
        NodeEntity node1 = nodeDao.save(nodeEntity);
        nodeVersion.setNid(nodeEntity.getNid());



        nodeVersionDao.save(nodeVersion);

        return node1;
    }

    private NodeRevisionEntity createOneVersion() {
        NodeRevisionEntity version = new NodeRevisionEntity();
        version.setRevision_timestamp(new Date().getTime()/1000);
        return nodeVersionDao.save(version);
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


    private void createChild(String fsid, String name, int depth, List<Long> path) throws InterruptedException {

        List<PcsItem> list = pcsApi.getChildItem(fsid);

        for (PcsItem item : list) {
            BookEntity bookEntity = new BookEntity();
            bookEntity.setPid(path.get(depth - 1));
            bookEntity.setBid(path.get(0));
            bookEntity.setDepth(depth);
            for (int i = 0; i < path.size(); i++) {
                setN(bookEntity, i, path.get(i));
            }
            //更新当前node的nid
            setN(bookEntity, depth + 1, bookEntity.getNid());
        }
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
