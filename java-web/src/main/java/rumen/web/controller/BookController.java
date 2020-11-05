//package rumen.web.controller;
package rumen.web.controller;
import rumen.web.dto.CommonResult;
import rumen.web.dto.form.SyncForm;
import rumen.web.entity.node.BookEntity;
import rumen.web.service.BookService;
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


}
