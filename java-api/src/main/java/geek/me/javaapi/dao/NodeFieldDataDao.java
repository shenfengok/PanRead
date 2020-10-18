package geek.me.javaapi.dao;

import geek.me.javaapi.entity.node.NodeFiledDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeFieldDataDao extends JpaRepository<NodeFiledDataEntity,Long> {
    NodeFiledDataEntity findByNid(long nid);
}
