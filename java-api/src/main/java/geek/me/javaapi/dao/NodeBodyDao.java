package geek.me.javaapi.dao;


import geek.me.javaapi.entity.node.NodeBodyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeBodyDao extends JpaRepository<NodeBodyEntity,Long> {
    NodeBodyEntity findByNid(long nid);
    NodeBodyEntity findFirstByBodyNotOrderByNidDesc(String empty);

    List<NodeBodyEntity> findAllByBodyIsNotNullAndBodyIsNot(String empty, Pageable pageable);
}
