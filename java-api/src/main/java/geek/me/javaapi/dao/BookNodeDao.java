package geek.me.javaapi.dao;


import geek.me.javaapi.entity.AccountEntity;
import geek.me.javaapi.entity.BookNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
@Deprecated
public interface BookNodeDao  {
    BookNodeEntity findByFsid(String fsId);
}
