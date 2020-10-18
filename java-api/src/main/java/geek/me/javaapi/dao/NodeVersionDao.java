package geek.me.javaapi.dao;

import geek.me.javaapi.entity.revision.NodeRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeVersionDao extends JpaRepository<NodeRevisionEntity,Long> {
}
