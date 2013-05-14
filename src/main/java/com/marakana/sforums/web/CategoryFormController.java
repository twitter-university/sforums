
package com.marakana.sforums.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.marakana.sforums.dao.CategoryDao;
import com.marakana.sforums.domain.Category;

@Controller
@RequestMapping("/category_form.html")
@SessionAttributes("category")
public class CategoryFormController {
    private final CategoryDao dao;

    @Autowired
    public CategoryFormController(CategoryDao dao) {
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView setupForm(@RequestParam(value = "id", required = false)
    Long id) {
        Category category = id == null ? new Category() : this.dao.getById(id);
        return new ModelAndView("categoryForm").addObject(category);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(new String[] {
                "name", "description"
        });
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("category")
    @Valid
    Category category, BindingResult result, SessionStatus status) {
        if (!result.hasErrors()) {
            try {
                this.dao.save(category);
                status.setComplete();
                return "redirect:category_form.html?id=" + category.getId() + "&success=true";
            } catch (DataIntegrityViolationException e) {
                result.rejectValue("name", "DuplicateNameFailure");
            } catch (ConcurrencyFailureException e) {
                result.reject("ConcurrentModificatonFailure");
            }
        }
        return "categoryForm";
    }
}
