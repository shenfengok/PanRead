package rumen.web.controller;

import geek.me.javaapi.dto.CommonResult;
import geek.me.javaapi.dto.form.SyncForm;
import geek.me.javaapi.entity.node.BookEntity;
import geek.me.javaapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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

    @RequestMapping("saveMe")
    public CommonResult saveMe() throws Exception {
        //同步到pan
        bookService.saveMe();
        //boolean res = bookService.sync(form);

        return CommonResult.success();
    }

    @RequestMapping("export")
    public CommonResult export(HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setHeader("Content-Disposition", "attachment;fileName=export.sql");
        String content =  bookService.genSql();;
        try {
            OutputStream os = response.getOutputStream();
            os.write(content.getBytes(StandardCharsets.UTF_8));
            os.close();
        } catch (IOException e) {
            System.out.println("文件下载失败"+ e);
        }

        return CommonResult.success();
    }
}
