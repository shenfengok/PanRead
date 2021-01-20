package geek.me.javaapi.dao;

import geek.me.javaapi.entity.QueueEntity;
import geek.me.javaapi.entity.UpdateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UpdateDao extends JpaRepository<UpdateEntity,Long> {

    QueueEntity findByName(String name);


}
