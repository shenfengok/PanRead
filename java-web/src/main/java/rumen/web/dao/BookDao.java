package rumen.web.dao;


import geek.me.javaapi.entity.node.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao extends JpaRepository<BookEntity,Long> {
    BookEntity findByNid(long nid);
}
