package rumen.web.dao;

import rumen.web.entity.node.BookFieldFsidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFieldFsidDao extends JpaRepository<BookFieldFsidEntity,Long> {
    BookFieldFsidEntity findByFsid(String fsid);
}
