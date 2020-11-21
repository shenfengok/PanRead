package geek.me.javaapi.dao;

import geek.me.javaapi.entity.node.BookFieldFsidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFieldFsidDao extends JpaRepository<BookFieldFsidEntity,Long> {
    BookFieldFsidEntity findByFsid(String fsid);
    BookFieldFsidEntity findByBookId(long bookId);
}
