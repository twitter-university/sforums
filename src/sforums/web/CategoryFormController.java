package sforums.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import sforums.dao.CategoryDao;
import sforums.domain.Category;

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
	public ModelAndView setupForm(
			@RequestParam(value = "id", required = false) Long id) {
		Category category = id == null ? new Category() : this.dao.getById(id);
		return new ModelAndView("categoryForm").addObject("category", category);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("category") Category category,
			BindingResult result, SessionStatus status) {
		if (!result.hasErrors()) {
			this.dao.save(category);
			status.setComplete();
			return "redirect:category.html?id=" + category.getId();
		}
		return "categoryForm";
	}
}