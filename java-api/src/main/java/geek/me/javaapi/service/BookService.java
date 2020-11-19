package geek.me.javaapi.service;

import geek.me.javaapi.baidu.PcsApi;
import geek.me.javaapi.baidu.PcsConst;
import geek.me.javaapi.baidu.dto.PcsItem;
import geek.me.javaapi.baidu.dto.PcsItemView;
import geek.me.javaapi.baidu.service.PcsDownService;
import geek.me.javaapi.baidu.service.PcsTransService;
import geek.me.javaapi.dao.*;
import geek.me.javaapi.dto.form.SyncForm;
import geek.me.javaapi.entity.BookCheckEntity;
import geek.me.javaapi.entity.QueueEntity;
import geek.me.javaapi.entity.node.*;
import geek.me.javaapi.entity.revision.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private BookFieldFenleiDao bookFieldFenleiDao;

    @Autowired
    private BookFieldThumbDao bookFieldThumbDao;

    @Autowired
    private BookFieldCommentDao bookFieldCommentDao;

    @Autowired

    private BookFieldMediaDao bookFieldMediaDao;


    @Autowired
    private NodeFiledFenleiRevisionDao nodeFiledFenleiRevisionDao;

    @Autowired
    private NodeFiledCommentRevisionDao nodeFiledCommentRevisionDao;

    @Autowired
    private NodeFiledThumbRevisionDao nodeFiledThumbRevisionDao;

    @Autowired
    private NodeFiledMediaRevisionDao nodeFiledMediaRevisionDao;

    @Autowired
    private BookCheckDao bookCheckDao;


    public List<BookEntity> listAll() {
        return bookDao.findAll();
    }

    public boolean syncBook(QueueEntity qe) throws Exception {

        pcsTransService.del(qe.getName());
        Thread.sleep(3000);
        List<String> fsids = new ArrayList<>();
        fsids.add(String.valueOf(qe.getFsid()));
        pcsTransService.transfer(fsids, qe.getName());
        Thread.sleep(3000);
        PcsItemView view = new PcsItemView();
        view.setFsid(String.valueOf(qe.getFsid()));
        view.setTitle(qe.getName());
        view.setContent("");
        view.setIsdir(1);
        NodeEntity aBook = createRoot(view, 1, new ArrayList<>(), qe.getBase_path(), 0);


        return true;
    }


    /**
     * 创建一本书
     *
     * @param pv
     * @return
     */
    private NodeEntity createRoot(PcsItemView pv, int depth, List<Long> parentPath, String basePath, int force) throws Exception {

//        BookCheckEntity entity = bookCheckDao.findByFsidAndName(714389511080L, "media");
//        List<BookCheckEntity> lst = bookCheckDao.findAll();
        NodeEntity node1 = creatOneNode(pv.getFsid());

        long nid = node1.getNid();
        long vid = node1.getVid();
        createOneFsidEntity(pv.getFsid(), nid, vid);

        List<Long> newPath = new ArrayList<>();
        newPath.addAll(parentPath);
        newPath.add(nid);
        createOneBookEntity(nid, depth, newPath, pv.getIsdir());


        String empty = "";
        boolean isDir = pv.getIsdir() == 1;

        createOneNodeBody(nid, isDir ? empty : pv.getContent(), vid);

        createOneFieldData(nid, pv.getTitle(), vid);

        createOneMediaEntity(isDir ? empty : pv.getMedia(), nid, vid);

        createOneCommentEntity(isDir ? empty : pv.getComment(), nid, vid);

        createOneThumbEntity(isDir ? empty : pv.getThumb(), nid, vid);

        createOneFeileiEntity(1, nid, vid);


        if (pv.getIsdir() == 1) {
            List<PcsItemView> list = pcsApi.getChildItemView(pv.getFsid(), basePath);
            for (PcsItemView item : list) {

                fillCon(item);


                createRoot(item, depth + 1, newPath, basePath, force);
            }
        }

        return node1;
    }

    private void fillCon(PcsItemView item) throws Exception {
        if (item.getIsdir() == 1) {

            return;
        }
        //这里发现有了就退出，后面判断为空不会更新到数据库---hack

        boolean isSuccess = false;
        try {
            if (!checkExist(item.getFsid(), "net_content")) {
                String path = PcsConst.basePath + item.getContentPath();
                String netDownUrl = pcsDownService.netdiskLink(path, item.getTitle());
                boolean noError;
                int i = 0;
                Document doc = null;

                do {
                    try {
                        String con = pcsDownService.netContent(netDownUrl);
                        doc = Jsoup.parse(con);
                        noError = true;
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        noError = false;
                        i++;
                    }
                    if (doc == null) {
                        int a = 1;
                    }
                } while (!noError && i < 3);


                Elements els = doc.getElementsByTag("img");
                els.forEach(x -> {
                    String imgLink = x.attr("data-savepage-src");
                    x.attr("src", imgLink);
                    x.removeAttr("data-savepage-src");
                });

                String con = "";// 内容
                if (doc.getElementsByClass("_29HP61GA_0").size() > 0) {
                    con = doc.getElementsByClass("_29HP61GA_0").get(0).html();
                } else if (doc.getElementsByClass("_2c4hPkl9").size() > 0) {
                    con = doc.getElementsByClass("_2c4hPkl9").get(0).html();
                }
                item.setContent(con);

                String thumb = "";//封面图片
                if (doc.getElementsByClass("_3Jbcj4Iu_0").size() > 0) {
                    if (doc.getElementsByClass("_3Jbcj4Iu_0").get(0).getElementsByTag("img").size() > 0) {
                        thumb = doc.getElementsByClass("_3Jbcj4Iu_0").get(0).getElementsByTag("img").get(0).attr("src");
                    } else {
                        thumb = "";
                        System.out.println(path + "无封面");
                    }

                }

                item.setThumb(thumb);

                String comment = "";//评论内容

                if (doc.getElementsByClass("_1qhD3bdE_0").size() > 0) {
                    comment = doc.getElementsByClass("_1qhD3bdE_0").get(0).getElementsByTag("ul").html();
                }
                item.setComment(comment);


                isSuccess = !StringUtils.isEmpty(con) && !StringUtils.isEmpty(comment) && !StringUtils.isEmpty(thumb);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            markIfExist(item.getFsid(), "net_content", isSuccess ? 1 : 0);
        }
        if (!checkExist(item.getFsid(), "media")) {
            String mediaUrl = "";
            try {
                mediaUrl = pcsDownService.netdiskLink(PcsConst.basePath + item.getMediaPath(), item.getMediaTitle());
            } catch (Exception e) {
                System.out.println(e);
            }
            item.setMedia(mediaUrl);
            markIfExist(item.getFsid(), "media", !StringUtils.isEmpty(mediaUrl) ? 1 : 0);
        }


    }

    private void markIfExist(String fsid, String name, int got) {
        BookCheckEntity bookCheckEntity = bookCheckDao.findByFsidAndName(fsid, name);
        if (null == bookCheckEntity) {
            bookCheckEntity = new BookCheckEntity();
        }
        bookCheckEntity.setFsid(fsid);
        bookCheckEntity.setGot(got);
        bookCheckEntity.setName(name);
        bookCheckDao.saveAndFlush(bookCheckEntity);
    }

    private boolean checkExist(String fsid, String name) {
        BookCheckEntity entity = bookCheckDao.findByFsidAndName(fsid, name);
        return null != entity && 1 == entity.getGot();
    }

    private NodeFiledDataEntity createOneFieldData(long nid, String title, long vid) {
        NodeFiledDataEntity nodeFiledDataEntity = nodeFieldDataDao.findByNid(nid);
        if (null == nodeFiledDataEntity) {
            nodeFiledDataEntity = new NodeFiledDataEntity();
        }
        nodeFiledDataEntity.setNid(nid);
        nodeFiledDataEntity.setTitle(title);
        nodeFiledDataEntity.setVid(vid);
        long time = new Date().getTime() / 1000;
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
     *
     * @param nid
     * @param content
     */
    private NodeBodyEntity createOneNodeBody(long nid, String content, long vid) {
        NodeBodyEntity nodeBodyEntity = nodeBodyDao.findByNid(nid);
        if (null == nodeBodyEntity) {
            nodeBodyEntity = new NodeBodyEntity();
        }
        if (!StringUtils.isEmpty(content)) {
            nodeBodyEntity.setBody(content);
        }
        if (null == nodeBodyEntity.getBody()) {
            nodeBodyEntity.setBody("");
        }

        nodeBodyEntity.setNid(nid);
        nodeBodyEntity.setRevision_id(vid);

        //revision
        NodeBodyRevisionEntity nodeBodyRevisionEntity = new NodeBodyRevisionEntity();
        nodeBodyRevisionEntity.setBody(nodeBodyEntity.getBody());
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

        BookFieldFsidEntity result = bookFieldFsidDao.save(bookFieldFsidEntity);

        //revision
        NodeFieldFsidRevisionEntity nodeFieldFsidRevisionEntity = new NodeFieldFsidRevisionEntity();
        nodeFieldFsidRevisionEntity.setBookId(nid);
        nodeFieldFsidRevisionEntity.setFsid(fsid);
        nodeFieldFsidRevisionEntity.setRevision_id(vid);
        nodeFiledFsidRevisionDao.save(nodeFieldFsidRevisionEntity);


        return result;
    }

    private BookFieldMediaEntity createOneMediaEntity(String media, long nid, long vid) {

        BookFieldMediaEntity entity = bookFieldMediaDao.findByBookId(nid);

        if (null == entity) {
            entity = new BookFieldMediaEntity();
        }
        entity.setBookId(nid);
        if (!StringUtils.isEmpty(media)) {
            entity.setMedia(media);
        }

        if (entity.getMedia() == null) {
            entity.setMedia("");
        }
        entity.setRevision_id(vid);

        BookFieldMediaEntity result = bookFieldMediaDao.save(entity);

        //revision
        NodeFieldMediaRevisionEntity er = new NodeFieldMediaRevisionEntity();
        er.setBookId(nid);
        er.setMedia(entity.getMedia());
        er.setRevision_id(vid);
        nodeFiledMediaRevisionDao.save(er);


        return result;
    }

    private BookFieldCommentEntity createOneCommentEntity(String comment, long nid, long vid) {
        BookFieldCommentEntity entity = bookFieldCommentDao.findByBookId(nid);

        if (null == entity) {
            entity = new BookFieldCommentEntity();
        }
        entity.setBookId(nid);

        if (!StringUtils.isEmpty(comment)) {
            entity.setComment(comment);
        }
        if (null == entity.getComment()) {
            entity.setComment("");
        }

        entity.setRevision_id(vid);

        BookFieldCommentEntity result = bookFieldCommentDao.save(entity);

        //revision
        NodeFieldCommentRevisionEntity er = new NodeFieldCommentRevisionEntity();
        er.setBookId(nid);
        er.setComment(entity.getComment());
        er.setRevision_id(vid);
        nodeFiledCommentRevisionDao.save(er);


        return result;
    }

    private BookFieldFenleiEntity createOneFeileiEntity(int fenleiId, long nid, long vid) {
        BookFieldFenleiEntity entity = new BookFieldFenleiEntity();
        entity.setBookId(nid);
        entity.setFenleiId(fenleiId);
        entity.setRevision_id(vid);

        BookFieldFenleiEntity result = bookFieldFenleiDao.save(entity);

        //revision
        NodeFieldFenleiRevisionEntity er = new NodeFieldFenleiRevisionEntity();
        er.setBookId(nid);
        er.setFenleiId(fenleiId);
        er.setRevision_id(vid);
        nodeFiledFenleiRevisionDao.save(er);


        return result;
    }

    private BookFieldThumbEntity createOneThumbEntity(String thumb, long nid, long vid) {
        BookFieldThumbEntity entity = bookFieldThumbDao.findByBookId(nid);
        if (null == entity) {
            entity = new BookFieldThumbEntity();
        }
        entity.setBookId(nid);
        if (!StringUtils.isEmpty(thumb)) {
            entity.setThumb(thumb);
        }
        if (null == entity.getThumb()) {
            entity.setThumb("");
        }
        entity.setRevision_id(vid);

        BookFieldThumbEntity result = bookFieldThumbDao.save(entity);

        //revision
        NodeFieldThumbRevisionEntity er = new NodeFieldThumbRevisionEntity();
        er.setBookId(nid);
        er.setThumb(entity.getThumb());
        er.setRevision_id(vid);
        nodeFiledThumbRevisionDao.save(er);


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
        version.setRevision_timestamp(new Date().getTime() / 1000);
        return nodeVersionDao.save(version);
    }

    private BookEntity createOneBookEntity(long bookId, int depth, List<Long> path, int hasChild) {
        BookEntity book = bookDao.findByNid(bookId);
        if (null == book) {
            book = new BookEntity();
        }
        book.setNid(bookId);
        if (depth > 1) {
            book.setPid(path.get(depth - 2));
        } else {
            book.setPid(0);
        }

        book.setBid(path.get(0));
        book.setHasChildren(hasChild);
        book.setDepth(depth);
        for (int i = 0; i < path.size(); i++) {
            setN(book, i + 1, path.get(i));
        }
        //更新当前node的nid
//        setN(book, depth, book.getNid());
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

    public void queOne(SyncForm form) {
        QueueEntity entity = new QueueEntity();
        entity.setFsid(form.getFsId());
        entity.setBase_path(form.getBasePath());
        entity.setName(form.getName());
        entity.setTodo(1);
        queueDao.saveAndFlush(entity);
    }

    public void queList(SyncForm form) throws Exception {


        //这里都是目录，base path不用传
        List<PcsItemView> list = pcsApi.getChildItemView(form.getFsId().toString(), "");
        for (PcsItemView item : list) {
            QueueEntity entity = new QueueEntity();
            entity.setFsid(Long.valueOf(item.getFsid()));

            entity.setBase_path(form.getBasePath() + form.getName() + "/");
            entity.setName(item.getTitle());
            entity.setTodo(1);
            queueDao.save(entity);
        }


    }

    public void transfer() throws Exception {
        //first of first 同步到百度盘
        List<QueueEntity> list = queueDao.findByTodo(1);
        for (QueueEntity q : list) {
            syncBook(q);
        }

    }
}
