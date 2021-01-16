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
import geek.me.javaapi.repository.QueueRepository;
import geek.me.javaapi.util.FileUtil;
import geek.me.javaapi.util.SqlTextUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

import static geek.me.javaapi.service.BookOutlineService.splitKey;

@Service
public class BookFileSaveService {
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

    @Autowired
    private QueueRepository queueRepository;

    public static String fileLoc = "D:\\drupal_txt\\";


    public List<BookEntity> listAll() {
        return bookDao.findAll();
    }

    public boolean syncBook(QueueEntity qe) throws Exception {

//        pcsTransService.del(qe.getName());
//        Thread.sleep(2000);
//
//
        return true;
    }


    //    /**
//     * 创建一本书
//     *
//     * @param pv
//     * @return
//     */
//    private NodeEntity createRoot(PcsItemView pv, int depth, List<Long> parentPath, String basePath, int force) throws Exception {
//
////        BookCheckEntity entity = bookCheckDao.findByFsidAndName(714389511080L, "media");
////        List<BookCheckEntity> lst = bookCheckDao.findAll();
//        NodeEntity node1 = creatOneNode(pv.getFsid());
//
//        long nid = node1.getNid();
//        long vid = node1.getVid();
//        createOneFsidEntity(pv.getFsid(), nid, vid);
//
//        List<Long> newPath = new ArrayList<>();
//        newPath.addAll(parentPath);
//        newPath.add(nid);
//        createOneBookEntity(nid, depth, newPath, pv.getIsdir());
//
//
//        String empty = "";
//        boolean isDir = pv.getIsdir() == 1;
//
//        createOneNodeBody(nid, isDir ? empty : pv.getContent(), vid);
//
//        createOneFieldData(nid, pv.getTitle(), vid);
//
//        createOneMediaEntity(isDir ? empty : pv.getMedia(), nid, vid);
//
//        createOneCommentEntity(isDir ? empty : pv.getComment(), nid, vid);
//
//        createOneThumbEntity(isDir ? empty : pv.getThumb(), nid, vid);
//
//        createOneFeileiEntity(1, nid, vid);
//
//
//        if (pv.getIsdir() == 1) {
//            List<PcsItemView> list = pcsApi.getChildItemView(pv.getFsid(), basePath);
//            int time = 1;
//            while (time < 3) {
//                time++;
//                try {
//                    list = pcsApi.getChildItemView(pv.getFsid(), basePath);
//                } catch (Exception e) {
//                    System.out.println(e);
//                    Thread.sleep(1000);
//                    continue;
//                }
//                break;
//            }
//
//            for (PcsItemView item : list) {
//
//                fillCon(item);
//
//
//                createRoot(item, depth + 1, newPath, basePath, force);
//            }
//        }
//
//        return node1;
//    }
//
    private void createContent(PcsItemView pv, int depth, List<Long> parentPath, String basePath, int force) {

    }

    /**
     * 创建子book
     *
     * @return
     */
    private void createChild(String parentFsid, List<Long> parentPath, String basePath) throws Exception {


        List<PcsItemView> originList = null;
        int time = 1;
        while (time < 3) {
            time++;
            try {
                originList = pcsApi.getChildItemView(parentFsid, basePath);
            } catch (Exception e) {
                System.out.println(e);
                Thread.sleep(1000);
                continue;
            }
            break;
        }
        if (null == originList) return;//这里直接跳过到下一本

        List<PcsItemView> list = originList.stream().sorted(Comparator.comparing(PcsItemView::getTitle)).collect(Collectors.toList());
        for (PcsItemView item : list) {

            saveOutline(item, parentPath);
            //这里不做内容填充
//            fillCon(item);
//            saveCon(item, parentPath);
            saveCon(item);
        }

        for (PcsItemView item : list) {
            if (item.getIsdir() == 1) {
                createChild(item.getFsid(), item.getCurrentPath(), basePath);
            }

        }

    }

    //先这个，创建node
    private Long saveOutline(PcsItemView pv, List<Long> parentPath) {
        NodeEntity node1 = creatOneNode(pv.getFsid());

        long nid = node1.getNid();
        long vid = node1.getVid();
        pv.setNid(nid);
        pv.setVid(vid);
        createOneFsidEntity(pv.getFsid(), nid, vid);

        List<Long> currentPath = new ArrayList<>();
        currentPath.addAll(parentPath);
        currentPath.add(nid);
        pv.setCurrentPath(currentPath);
        pv.setDepth(parentPath.size() + 1);

        createOneBook(pv, currentPath);
        return nid;
    }

    private void saveCon(PcsItemView pv) {

        long nid = pv.getNid();
        long vid = pv.getVid();


        String empty = "";
        boolean isDir = pv.getIsdir() == 1;

        //这里只保存outline ,不做内容填充
        createOneNodeBody(nid, isDir ? empty : getFileName(nid, "body"), vid);

        createOneFieldData(nid, pv.getTitle(), vid);

        createOneMediaEntity(isDir ? empty : pv.getMedia(), nid, vid);

        createOneCommentEntity(isDir ? empty : pv.getComment(), nid, vid);

        createOneThumbEntity(isDir ? empty : pv.getThumb(), nid, vid);

        createOneFeileiEntity(1, nid, vid);


    }

    private String getFileName(Long nid, String type) {
        Long folder = nid % 64;
        String fileName = folder.toString() + "/" + nid.toString() + "_" + type + ".txt";
        return fileName;
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



    private BookEntity createOneBook(PcsItemView item, List<Long> currentPath) {
        BookEntity book = bookDao.findByNid(item.getNid());
        if (null == book) {
            book = new BookEntity();
        }
        book.setNid(item.getNid());
        int depth = item.getDepth();
        if (depth > 1) {
            book.setPid(currentPath.get(depth - 2));
        } else {
            book.setPid(0);
        }

        book.setBid(currentPath.get(0));
        book.setHasChildren(item.getIsdir());
        book.setDepth(depth);
        for (int i = 0; i < currentPath.size(); i++) {
            setN(book, i + 1, currentPath.get(i));
        }
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


    private void saveBody(NodeBodyEntity nodeBodyEntity){
        String[] paths = nodeBodyEntity.getBody().split(splitKey);
        if(paths.length == 3){
            String bodyPath = paths[0];
            String mediaPath = paths[1];
            String savePrefix = fileLoc +   paths[2];

            ContentParser contentParser = new ContentParser(bodyPath);
            if(contentParser.isSuccess()){
                FileUtil.writeFile(savePrefix+"body.txt",contentParser.getBody());
                FileUtil.writeFile(savePrefix+"comment.txt",contentParser.getComment());
                BookFieldThumbEntity entity = bookFieldThumbDao.findByBookId(nodeBodyEntity.getNid());
                entity.setThumb(contentParser.getThumb());
                bookFieldThumbDao.save(entity);
            }


        }else{
            System.out.println("错误：" + nodeBodyEntity.getNid() +"---"+ nodeBodyEntity.getBody() );
        }
    }

    public Integer savePage(int i) {
        PageRequest request = PageRequest.of(i+1,20);
        List<NodeBodyEntity> bodys = nodeBodyDao.findAllByBodyIsNotNullAndBodyIsNot("",request);
        if(bodys == null || bodys.size() <=0){
            return 0;
        }

        for(NodeBodyEntity en: bodys){
            saveBody(en);
        }

        return  bodys.size();

    }
}
