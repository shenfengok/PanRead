package rumen.web.dao;

import rumen.web.entity.revision.NodeFieldRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeFieldRevisionDao extends JpaRepository<NodeFieldRevisionEntity,Long> {

}
