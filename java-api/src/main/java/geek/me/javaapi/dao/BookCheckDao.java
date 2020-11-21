package geek.me.javaapi.dao;

import geek.me.javaapi.entity.BookCheckEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCheckDao extends JpaRepository<BookCheckEntity,Long> {
    BookCheckEntity findByFsidAndName(String fsid,String name);
    BookCheckEntity findFirstByGotOrderByIdDesc(int got);
    Page<BookCheckEntity> findAllByGot(int got, Pageable pagable);
}
