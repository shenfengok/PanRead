package geek.me.javaapi.dao;

import geek.me.javaapi.entity.revision.NodeFieldRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeFieldRevisionDao extends JpaRepository<NodeFieldRevisionEntity,Long> {

}
