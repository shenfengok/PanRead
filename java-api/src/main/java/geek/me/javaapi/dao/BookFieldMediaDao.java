package geek.me.javaapi.dao;

import geek.me.javaapi.entity.node.BookFieldFsidEntity;
import geek.me.javaapi.entity.node.BookFieldMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFieldMediaDao extends JpaRepository<BookFieldMediaEntity,Long> {
    BookFieldMediaEntity findByBookId(long nid);
}
