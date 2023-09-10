package org.calisto.hotel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {

    @RequestMapping("/swagger")
    public String getRedirectUrl() {
        return "redirect:swagger-ui.html";
    }

}
