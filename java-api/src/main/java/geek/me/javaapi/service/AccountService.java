package geek.me.javaapi.service;

import geek.me.javaapi.dao.AccountDao;
import geek.me.javaapi.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    public AccountEntity login(String userName, String password) {
        return accountDao.findByUserNameAndPassword(userName, password);
    }

    public AccountEntity insert(String name, String pwd) {
        AccountEntity entity = new AccountEntity() ;
        entity.setUserName(name);
        entity.setPassword(pwd);
        return accountDao.save(entity);
    }
}
