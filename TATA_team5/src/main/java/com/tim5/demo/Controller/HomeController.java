package com.tim5.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Sumejja on 22.07.2018..
 */

@Controller
public class HomeController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String firtsPage() {
        return "index";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "loginPage";
    }

    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public String adminPanel() {
        return "adminProfile";
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public String userPanel() {
        return "userProfile";
    }
}
