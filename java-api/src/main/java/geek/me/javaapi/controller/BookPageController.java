package geek.me.javaapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/all")
public class BookPageController {

    @RequestMapping("books")
    public String index(ModelMap map){
        map.addAttribute("title","test");
        return "home";
    }
}
