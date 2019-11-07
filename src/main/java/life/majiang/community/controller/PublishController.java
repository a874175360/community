package life.majiang.community.controller;

import life.majiang.community.mapper.QusetionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Qusetion;
import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QusetionMapper qusetionMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    private String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title==null || title==""){
            model.addAttribute("msg","标题不能为空");
            return "publish";
        }
        if (description==null||description==""){
            model.addAttribute("msg","问题补充不能为空");
            return "publish";
        }
        if (tag==null||tag==""){
            model.addAttribute("msg","标签不能为空");
            return "publish";
        }

        User user=null;

        //从数据库中查找user
        Cookie[] cookies = request.getCookies();
        if (cookies !=null&&cookies.length!=0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token"))
                {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if (user ==null){
            model.addAttribute("msg","用户未登录");
            return "publish";
        }

        Qusetion qusetion = new Qusetion();
        qusetion.setTitle(title);
        qusetion.setDescription(description);
        qusetion.setTag(tag);
        qusetion.setCreator(user.getId());
        qusetion.setGmtCreate(System.currentTimeMillis());
        qusetion.setGmtModified(qusetion.getGmtCreate());

        qusetionMapper.create(qusetion);


        return "redirect:/";
    }
}
