package rumen.web.dao;


import rumen.web.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao  extends JpaRepository<AccountEntity,Long> {
    AccountEntity findByUserNameAndPassword(String name,String pwd);
}
