package geek.me.javaapi.dao;

import geek.me.javaapi.entity.node.BookFieldThumbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFieldThumbDao extends JpaRepository<BookFieldThumbEntity,Long> {
    BookFieldThumbEntity findByBookId(long nid);
}
