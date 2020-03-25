package cn.easy.graduation.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class indexController {

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request){
        return "index/index";
    }
}
