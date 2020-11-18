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
    @RequestMapping("queOne")
    public CommonResult queOne( @RequestBody SyncForm form) throws InterruptedException {
        //仅仅加入队列
        bookService.queOne(form);
        //boolean res = bookService.sync(form);

        return CommonResult.success();
    }

    @RequestMapping("queList")
    public CommonResult queList( @RequestBody SyncForm form) throws Exception {
        //仅仅加入队列
        bookService.queList(form);
        //boolean res = bookService.sync(form);

        return CommonResult.success();
    }

    @RequestMapping("transfer")
    public CommonResult transfer() throws Exception {
        //同步到pan
        bookService.transfer();
        //boolean res = bookService.sync(form);

        return CommonResult.success();
    }
}
