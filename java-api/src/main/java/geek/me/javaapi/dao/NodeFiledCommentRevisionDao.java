package geek.me.javaapi.dao;

import geek.me.javaapi.entity.revision.NodeFieldCommentRevisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeFiledCommentRevisionDao extends JpaRepository<NodeFieldCommentRevisionEntity,Long> {

}
