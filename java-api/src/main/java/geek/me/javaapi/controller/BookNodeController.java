package geek.me.javaapi.controller;

import geek.me.javaapi.dto.CommonResult;
import geek.me.javaapi.dto.form.SyncForm;
import geek.me.javaapi.dto.query.NodeTypeQuery;
import geek.me.javaapi.dto.view.BookNodeView;
import geek.me.javaapi.service.BookNodeService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@RestController
@RequestMapping("api/node")
@Deprecated
public class BookNodeController {

//    @Autowired
    private BookNodeService bookNodeService;

    @RequestMapping("queryType")
    public CommonResult<BookNodeView> queryType( @RequestBody NodeTypeQuery nodeTypeQuery){
        BookNodeView res = bookNodeService.queryType(nodeTypeQuery.getFsId(),nodeTypeQuery.getName());
        return CommonResult.success(res);
    }

    @RequestMapping("queryChildType")
    public CommonResult<List<BookNodeView>> queryChildType(@RequestBody NodeTypeQuery nodeTypeQuery) throws InterruptedException {
        List<BookNodeView> res = bookNodeService.queryChildType(nodeTypeQuery.getFsId(),nodeTypeQuery.getName());
        return CommonResult.success(res);
    }


    @RequestMapping("markNodeType")
    public CommonResult markNodeType(@RequestBody SyncForm form){

        BookNodeView res = bookNodeService.markNodeType(form.getFsId(),form.getName(),form.getType());
        return CommonResult.success(res);
    }

    @RequestMapping("updateParent")
    public CommonResult updateParent( @RequestBody SyncForm form){
        BookNodeView res = bookNodeService.updateParent(form.getFsId());
        return CommonResult.success();
    }

    @RequestMapping("syncParent")
    public CommonResult syncParent( @RequestBody SyncForm form){
        BookNodeView res = bookNodeService.syncParent(form.getFsId());
        return CommonResult.success();
    }

}
