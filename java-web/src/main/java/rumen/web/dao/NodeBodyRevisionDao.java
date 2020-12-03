package rumen.web.dao;

import geek.me.javaapi.entity.revision.NodeBodyRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeBodyRevisionDao extends JpaRepository<NodeBodyRevisionEntity,Long> {

}
