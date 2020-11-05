package rumen.web.dao;

import rumen.web.entity.revision.NodeFieldFsidRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeFiledFsidRevisionDao extends JpaRepository<NodeFieldFsidRevisionEntity,Long> {

}
