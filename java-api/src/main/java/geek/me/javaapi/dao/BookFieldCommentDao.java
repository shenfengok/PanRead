package geek.me.javaapi.dao;

import geek.me.javaapi.entity.node.BookFieldCommentEntity;
import geek.me.javaapi.entity.node.BookFieldMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFieldCommentDao extends JpaRepository<BookFieldCommentEntity,Long> {
    BookFieldCommentEntity findByBookId(long nid);
}
