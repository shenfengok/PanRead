package geek.me.javaapi.service;

import geek.me.javaapi.baidu.PcsApi;
import geek.me.javaapi.baidu.dto.PcsItemView;
import geek.me.javaapi.dao.BookDao;
import geek.me.javaapi.dao.NodeFieldDataDao;
import geek.me.javaapi.dao.UpdateDao;
import geek.me.javaapi.entity.QueueEntity;
import geek.me.javaapi.entity.UpdateEntity;
import geek.me.javaapi.entity.node.BookEntity;
import geek.me.javaapi.entity.node.NodeBodyEntity;
import geek.me.javaapi.entity.node.NodeFiledDataEntity;
import geek.me.javaapi.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
public class SpiderService {

    @Autowired
    private PcsApi pcsApi;

    @Autowired
    private BookOutlineService bookOutlineService;

    @Autowired
    private BookFileSaveService bookFileSaveService;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private NodeFieldDataDao  nodeFieldDataDao;

    @Autowired
    private UpdateDao updateDao;

    public void queJike() throws Exception {
        String jikeFinish = "";

        String jikeUpdate = "";
        List<PcsItemView> listTop = pcsApi.getAllChild("467881182899614");


        for(PcsItemView pcsItemView: listTop){
            if(pcsItemView.getTitle().contains("01-专栏课")){
                jikeFinish = pcsItemView.getFsid();
            }

            if(pcsItemView.getTitle().contains("00-更新中的专栏")){
                jikeUpdate = pcsItemView.getFsid();
            }
        }

        if(StringUtils.isEmpty(jikeFinish) || StringUtils.isEmpty(jikeUpdate)){
            throw new Exception("获取fsid失败，请重新检查");
        }



        //这里都是目录，base path不用传
        List<PcsItemView> list = pcsApi.getAllChild(jikeFinish);
        List<PcsItemView> listFinish = list.stream().flatMap(l->{//1-50
            List<PcsItemView> list2 = new ArrayList<>();
            try {
                list2 = pcsApi.getAllChild(l.getFsid());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list2.stream();
        }).collect(Collectors.toList());

        for(PcsItemView piv : listFinish){
            queueRepository.push(piv,1);
        }


        //这里都是目录，base path不用传
        List<PcsItemView> listUpdate = pcsApi.getAllChild(jikeUpdate);


        for(PcsItemView piv : listUpdate){
            queueRepository.push(piv,0);
        }
    }

    public void saveOutLine() throws Exception {
        List<QueueEntity> list  = queueRepository.findAll();

        for(QueueEntity qe : list){
            Long bookId = bookOutlineService.saveBookOutLine(qe);
            qe.setBookId(bookId);
            queueRepository.update(qe);
        }

    }

    public void saveFiles() {
        List<NodeBodyEntity> fails = new ArrayList<>();
        for(int i = 0 ; i < 20000;i++){
           Integer count =  bookFileSaveService.savePage(i,fails);
           if(count <= 0){
               break;
           }
        }
        if(fails.size() > 0){
            //todo 失败记录，用于更新猫盘
            List<Long> nid = fails.stream().map(x->x.getNid()).collect(Collectors.toList());
            List<BookEntity> books = bookDao.findAllById(nid);
            List<Long> bookIds = books.stream().map(x->x.getP1()).distinct().collect(Collectors.toList());
            List<NodeFiledDataEntity> datas = nodeFieldDataDao.findAllById(bookIds);
            List<UpdateEntity> updates = new ArrayList<>();
            for(NodeFiledDataEntity d : datas){
                UpdateEntity entity = new UpdateEntity();
                entity.setBase_path("");
                entity.setBookId(d.getNid());
                entity.setName(d.getTitle());
                updates.add(entity);
            }
            updateDao.saveAll(updates);
        }
    }
}
