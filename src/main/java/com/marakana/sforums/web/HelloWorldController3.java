
package com.marakana.sforums.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController3 {

    @RequestMapping(value = "/HelloWorld3.html", method = RequestMethod.GET)
    public ModelAndView hello() {
        String message = "Hello World by Annotation!!!";
        ModelAndView mav = new ModelAndView();
        mav.setViewName("helloWorld");
        mav.addObject("message", message);
        return mav;
    }
}
