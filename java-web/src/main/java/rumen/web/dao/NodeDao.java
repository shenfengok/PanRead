package rumen.web.dao;

import geek.me.javaapi.entity.node.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeDao extends JpaRepository<NodeEntity,Long> {

    NodeEntity findByNid(long nid);
}
