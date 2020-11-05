package rumen.web.dao;


import rumen.web.entity.node.NodeBodyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeBodyDao extends JpaRepository<NodeBodyEntity,Long> {
    NodeBodyEntity findByNid(long nid);
}
