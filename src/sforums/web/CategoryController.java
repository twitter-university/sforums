package sforums.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sforums.dao.CategoryDao;

/**
 * An alternative way to list/view/delete categories. This controller supports
 * the following URLs: - /category/list.html - /category/123/view.html -
 * /category/view.html?id=123 - /category/123/delete.html -
 * /category/delete.html?id=123 where 123 is some category id
 * 
 * REST
 * delete category 123: DELETE /categories/123
 * add new category: POST /categories
 * update category 123: POST /categories/123
 * update category 123: PUT /categories/123
 * get category 123: GET /categories/123
 * get all categories: GET /categories
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
	private final CategoryDao dao;

	@Autowired
	public CategoryController(CategoryDao dao) {
		this.dao = dao;
	}

	@RequestMapping("/list.html")
	public ModelAndView list() {
		return new ModelAndView("categoryList").addObject(this.dao.getAll());
	}

	@RequestMapping({ "/{categoryId}/view.html" })
	public ModelAndView viewByPath(@PathVariable("categoryId") Long categoryId) {
		return view(categoryId);
	}

	@RequestMapping({ "/view.html" })
	public ModelAndView viewByParam(@RequestParam("id") Long categoryId) {
		return view(categoryId);
	}

	@RequestMapping({ "/{categoryId}/delete.html" })
	public String deleteByPath(@PathVariable("categoryId") Long categoryId) {
		return delete(categoryId);
	}

	@RequestMapping({ "/delete.html" })
	public String deleteByParam(@RequestParam("id") Long categoryId) {
		return delete(categoryId);
	}

	private ModelAndView view(Long categoryId) {
		return new ModelAndView("categoryView").addObject(this.dao
				.getById(categoryId));
	}

	private String delete(Long categoryId) {
		this.dao.deleteById(categoryId);
		return "redirect:/category/list.html";
	}
}