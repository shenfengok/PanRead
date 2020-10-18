package geek.me.javaapi.dao;

import geek.me.javaapi.entity.revision.NodeFieldFsidRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeFiledFsidRevisionDao extends JpaRepository<NodeFieldFsidRevisionEntity,Long> {

}
