package rumen.web.dao;

import geek.me.javaapi.entity.revision.NodeFieldMediaRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeFiledMediaRevisionDao extends JpaRepository<NodeFieldMediaRevisionEntity,Long> {

}
