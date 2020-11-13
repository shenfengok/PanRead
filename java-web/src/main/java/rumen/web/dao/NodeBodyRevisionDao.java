package rumen.web.dao;

import rumen.web.entity.revision.NodeBodyRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeBodyRevisionDao extends JpaRepository<NodeBodyRevisionEntity,Long> {

}