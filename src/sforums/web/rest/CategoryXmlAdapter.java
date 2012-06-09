package sforums.web.rest;

import org.springframework.beans.factory.annotation.Autowired;

import sforums.dao.CategoryDao;
import sforums.domain.Category;

public class CategoryXmlAdapter extends IdentifiableEntityXmlAdapter<Category> {

	@Autowired
	public CategoryXmlAdapter(CategoryDao dao) {
		super(dao);
	}
	
	public CategoryXmlAdapter() {
		this(null);
	}
}
