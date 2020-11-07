package geek.me.javaapi.service;

import geek.me.javaapi.baidu.PcsApi;
import geek.me.javaapi.baidu.PcsConst;
import geek.me.javaapi.baidu.dto.PcsItem;
import geek.me.javaapi.baidu.dto.PcsItemView;
import geek.me.javaapi.baidu.service.PcsDownService;
import geek.me.javaapi.baidu.service.PcsTransService;
import geek.me.javaapi.dao.*;
import geek.me.javaapi.dto.form.SyncForm;
import geek.me.javaapi.entity.QueueEntity;
import geek.me.javaapi.entity.node.*;
import geek.me.javaapi.entity.revision.NodeBodyRevisionEntity;
import geek.me.javaapi.entity.revision.NodeFieldFsidRevisionEntity;
import geek.me.javaapi.entity.revision.NodeFieldRevisionEntity;
import geek.me.javaapi.entity.revision.NodeRevisionEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private PcsTransService pcsTransService;

    @Autowired
    private QueueDao queueDao;

    @Autowired
    private PcsDownService pcsDownService;



    public List<BookEntity> listAll() {
        return bookDao.findAll();
    }

    public boolean syncBook(QueueEntity qe) throws Exception {

//        pcsTransService.del(qe.getName());
//        Thread.sleep(3000);
//        List<String > fsids = new ArrayList<>();
//        fsids.add(String.valueOf(qe.getFsid()));
//        pcsTransService.transfer(fsids,qe.getName());
//        Thread.sleep(3000);
        PcsItemView view = new PcsItemView();
        view.setFsid(String.valueOf(qe.getFsid()));
        view.setTitle(qe.getName());
        view.setContent("");
        view.setIsdir(1);
        NodeEntity aBook = createRoot(view,1,new ArrayList<>(), qe.getBase_path());


        return true;
    }



    /**
     * 创建一本书
     *
     * @param pv
     * @return
     */
    private NodeEntity createRoot(PcsItemView pv,int depth,List<Long> parentPath,String basePath) throws Exception {


        NodeEntity node1 = creatOneNode(pv.getFsid());

        long nid = node1.getNid();
        long vid = node1.getVid();
        createOneFsidEntity(pv.getFsid(), nid,vid);

        List<Long> newPath = new ArrayList<>();
        newPath.addAll(parentPath);
        newPath.add(nid);
        createOneBookEntity(nid, depth, newPath,pv.getIsdir());


        createOneNodeBody(nid,pv.getContent(),vid);

        createOneFieldData(nid,pv.getTitle(),vid);


        if(pv.getIsdir() == 1){
            List<PcsItemView> list = pcsApi.getChildItemView(pv.getFsid(),basePath);
            for (PcsItemView item : list) {
                fillCon(item);
                String mediaUrl = pcsDownService.netdiskLink(item.getMediaPath(),item.getMediaTitle());
                item.setMedia(mediaUrl);

                createRoot(item,depth + 1,newPath,basePath);
            }
        }

        return node1;
    }

    private void fillCon(PcsItemView item) {
        if(item.getIsdir() == 1){

            return ;
        }

        try{
            String path = PcsConst.basePath + item.getContentPath();
            String netDownUrl = pcsDownService.netdiskLink(path,item.getTitle());

            Document doc= Jsoup.connect(netDownUrl).timeout(30000).execute().parse();
            Elements els =doc.getElementsByTag("img");
            els.forEach(x->{
                String imgLink = x.attr("data-savepage-src");
                x.attr("src",imgLink);
                x.removeAttr("data-savepage-src");
            });

            String con = "";// 内容
            if(doc.getElementsByClass("_29HP61GA_0").size() > 0){
                con =  doc.getElementsByClass("_29HP61GA_0").get(0).html();
            }else if(doc.getElementsByClass("_2c4hPkl9").size() > 0){
                con =  doc.getElementsByClass("_2c4hPkl9").get(0).html();
            }
            item.setContent(con);

            String thumb = "";//封面图片
            if(doc.getElementsByClass("_3Jbcj4Iu_0").size() > 0){
                 thumb = doc.getElementsByClass("_3Jbcj4Iu_0").get(0).getElementsByTag("img").get(0).attr("src");
            }

            item.setThumb(thumb);

            String comment = "";//评论内容

            if(doc.getElementsByClass("_1qhD3bdE_0").size() > 0){
                comment = doc.getElementsByClass("_1qhD3bdE_0").get(0).getElementsByTag("ul").html();
            }
            item.setComment(comment);

        }catch (Exception e){
            System.out.println(e);
        }
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

    public void addQueue(SyncForm form) {
        QueueEntity entity = new QueueEntity();
        entity.setFsid(form.getFsId());
        entity.setBase_path(form.getBasePath());
        entity.setName(form.getName());
        queueDao.saveAndFlush(entity);
    }

    public void transfer() throws InterruptedException {
        //first of first 同步到百度盘
        List<QueueEntity> list = queueDao.findAll();
        for(QueueEntity q:list) {
            syncBook(q);
        }

    }
}
