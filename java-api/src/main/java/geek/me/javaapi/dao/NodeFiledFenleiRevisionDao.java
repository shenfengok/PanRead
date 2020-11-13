package geek.me.javaapi.dao;

import geek.me.javaapi.entity.revision.NodeFieldFenleiRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeFiledFenleiRevisionDao extends JpaRepository<NodeFieldFenleiRevisionEntity,Long> {

}
