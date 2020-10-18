package geek.me.javaapi.controller;

import geek.me.javaapi.dto.CommonResult;
import geek.me.javaapi.dto.form.SyncForm;
import geek.me.javaapi.entity.node.BookEntity;
import geek.me.javaapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("listAll")
    public CommonResult<List<BookEntity>> listAll(){
        List<BookEntity> res = bookService.listAll();
        return CommonResult.success(res);
    }
    @RequestMapping("sync")
    public CommonResult sync( @RequestBody SyncForm form) throws InterruptedException {
        boolean res = bookService.sync(form);

        return CommonResult.success();
    }

}
