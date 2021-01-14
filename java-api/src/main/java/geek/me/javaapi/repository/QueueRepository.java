package geek.me.javaapi.repository;

import geek.me.javaapi.baidu.dto.PcsItemView;
import geek.me.javaapi.dao.QueueDao;
import geek.me.javaapi.entity.QueueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Component
public class QueueRepository {

    @Autowired
    private QueueDao queueDao;

    public void push(PcsItemView item,Integer finish){


        QueueEntity entity  =queueDao.findByName(item.getTitle());
        if(null == entity){
            entity = new QueueEntity();
        }
        entity.setFsid(item.getFsid());

        entity.setBase_path(item.getParentPath());
        entity.setName(item.getTitle());
        entity.setTodo(1);
        entity.setFinish(finish);
        queueDao.save(entity);
    }


    public List<QueueEntity> findAll() {
        List<QueueEntity> ques = queueDao.findAll();
//        return queueDao.findAllOrderByNameDesc();
        return ques.stream().sorted(Comparator.comparing(QueueEntity::getName)).collect(Collectors.toList());

    }

    public void update(QueueEntity qe) {
        queueDao.save(qe);
    }
}
