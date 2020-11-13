package geek.me.javaapi.dao;

import geek.me.javaapi.entity.revision.NodeFieldCommentRevisionEntity;
import geek.me.javaapi.entity.revision.NodeFieldThumbRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeFiledThumbRevisionDao extends JpaRepository<NodeFieldThumbRevisionEntity,Long> {

}
