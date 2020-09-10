package geek.me.javaapi.controller;

import geek.me.javaapi.dto.CommonResult;
import geek.me.javaapi.dto.query.NodeTypeQuery;
import geek.me.javaapi.dto.view.BookNodeView;
import geek.me.javaapi.entity.BookNodeEntity;
import geek.me.javaapi.service.BookNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/node")
public class BookNodeController {

    @Autowired
    private BookNodeService bookNodeService;

    @RequestMapping("queryType")
    public CommonResult<BookNodeView> queryType( @RequestBody NodeTypeQuery nodeTypeQuery){
        BookNodeView res = bookNodeService.queryType(nodeTypeQuery.getFsId());
        return CommonResult.success(res);
    }
}
