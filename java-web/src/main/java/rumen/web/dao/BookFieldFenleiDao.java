package rumen.web.dao;

import geek.me.javaapi.entity.node.BookFieldFenleiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFieldFenleiDao extends JpaRepository<BookFieldFenleiEntity,Long> {
}
