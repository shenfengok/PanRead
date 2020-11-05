package rumen.web.service;

import rumen.web.dao.AccountDao;
import rumen.web.entity.AccountEntity;
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
