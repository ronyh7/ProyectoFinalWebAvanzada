package com.progwebavanzada.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rony- on 12/8/2016.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("/")
    public String login(){
        return "redirect:/login";
    }
}
