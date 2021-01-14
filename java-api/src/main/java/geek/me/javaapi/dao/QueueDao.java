package geek.me.javaapi.dao;

import geek.me.javaapi.entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueueDao extends JpaRepository<QueueEntity,Long> {
    QueueEntity findByFsid(String fsid);
    QueueEntity findByName(String name);

    List<QueueEntity> findByTodoOrderByName(int i);
    List<QueueEntity> findByBookIdIsNull();
    QueueEntity findByBookId(Long bookId);
    QueueEntity findFirstById(Long id);
//    List<QueueEntity> findAllOrderByNameDesc();
}
