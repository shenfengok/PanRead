package geek.me.javaapi.dao;


import geek.me.javaapi.entity.AccountEntity;
import geek.me.javaapi.entity.BookNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookNodeDao extends JpaRepository< BookNodeEntity,String> {
    BookNodeEntity findByFsid(String fsId);
}
