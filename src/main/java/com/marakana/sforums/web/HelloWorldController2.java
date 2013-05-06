
package com.marakana.sforums.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

public class HelloWorldController2 extends ParameterizableViewController {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String message = "Hello World by Flexible View!!!";
        ModelAndView mav = new ModelAndView(super.getViewName());
        mav.addObject("message", message);
        return mav;
    }
}
