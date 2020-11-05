package rumen.web.dao;

import rumen.web.entity.revision.NodeRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeVersionDao extends JpaRepository<NodeRevisionEntity,Long> {
}
