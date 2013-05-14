
package com.marakana.sforums.web;

import java.util.List;

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
import com.marakana.sforums.dao.ForumDao;
import com.marakana.sforums.domain.Category;
import com.marakana.sforums.domain.Forum;

@Controller
@RequestMapping("/forum_form.html")
@SessionAttributes("forum")
public class ForumFormController {
    private final ForumDao forumDao;

    private final CategoryDao categoryDao;

    @Autowired
    public ForumFormController(ForumDao forumDao, CategoryDao categoryDao) {
        this.forumDao = forumDao;
        this.categoryDao = categoryDao;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Category.class, new IdentifiableEntityEditor(this.categoryDao));
        binder.setAllowedFields(new String[] {
                "category", "name", "description"
        });
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView setupForm(@RequestParam(value = "id", required = false)
    Long id, @RequestParam(value = "categoryId", required = false)
    Long categoryId) {
        Forum forum;
        if (id == null) {
            forum = new Forum();
            if (categoryId != null) {
                forum.setCategory(this.categoryDao.getById(categoryId));
            }
        } else {
            forum = this.forumDao.getById(id);
        }
        return new ModelAndView("forumForm").addObject(forum);
    }

    @ModelAttribute("categoryList")
    public List<Category> addCategoryListToModel() {
        return this.categoryDao.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("forum")
    @Valid
    Forum forum, BindingResult result, SessionStatus status) {
        if (!result.hasErrors()) {
            try {
                this.forumDao.save(forum);
                status.setComplete();
                return "redirect:forum.html?id=" + forum.getId() + "&success=true";
            } catch (DataIntegrityViolationException e) {
                result.rejectValue("name", "DuplicateNameFailure");
            } catch (ConcurrencyFailureException e) {
                result.reject("ConcurrentModificatonFailure");
            }
        }
        return "forumForm";
    }
}
