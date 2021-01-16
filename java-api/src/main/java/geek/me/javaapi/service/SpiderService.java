package geek.me.javaapi.service;

import geek.me.javaapi.baidu.PcsApi;
import geek.me.javaapi.baidu.dto.PcsItemView;
import geek.me.javaapi.entity.QueueEntity;
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
        for(int i = 0 ; i < 20000;i++){
           Integer count =  bookFileSaveService.savePage(i);
           if(count <= 0){
               break;
           }
        }
    }
}
