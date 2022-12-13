package moshare.easycopy.controller;


import moshare.easycopy.entity.News;
import moshare.easycopy.service.EasyCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/easyCopy")
public class EasyCopyController {
    @Autowired
    private EasyCopyService easycopyService;

    @GetMapping("/copy")
    @ResponseBody
    public News getNewsByUrl(@RequestParam("url") String url,@RequestParam("siteType")String siteType,@RequestParam(value = "comments",required = false) String comments){
        return easycopyService.getNewsByUrl(url,siteType,comments);
    }
}
