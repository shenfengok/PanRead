package rumen.web.controller;

import rumen.web.dto.CommonResult;
import rumen.web.dto.form.LoginForm;
import rumen.web.entity.AccountEntity;
import rumen.web.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AccountController {

    @Autowired
    private AccountService service;

    @RequestMapping("/login/account")
    public CommonResult login (@RequestBody LoginForm form){
        AccountEntity accountEntity = service.login(form.getUserName(),form.getPassword());
        if(null != accountEntity){
            return CommonResult.success(accountEntity);
        }
        return CommonResult.fail("登录失败");
    }

    @RequestMapping("/login/init")
    public CommonResult init (){
        AccountEntity accountEntity = service.insert("shenfeng","ipqmtd123");
        if(null != accountEntity){
            return CommonResult.success(accountEntity);
        }
        return CommonResult.fail("登录失败");
    }


}
