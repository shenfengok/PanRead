package geek.me.javaapi.dao;

import geek.me.javaapi.entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueDao extends JpaRepository<QueueEntity,Long> {
    QueueEntity findByFsid(long fsid);

    List<QueueEntity> findByTodoOrderByName(int i);
}
