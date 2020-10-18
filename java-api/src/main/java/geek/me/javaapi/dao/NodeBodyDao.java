package geek.me.javaapi.dao;


import geek.me.javaapi.entity.node.NodeBodyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeBodyDao extends JpaRepository<NodeBodyEntity,Long> {
    NodeBodyEntity findByNid(long nid);
}
