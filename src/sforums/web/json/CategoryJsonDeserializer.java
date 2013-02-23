package sforums.web.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sforums.dao.CategoryDao;
import sforums.domain.Category;

@Component
public class CategoryJsonDeserializer extends
		IdentifiableEntityJsonDeserializer<Category> {
	@Autowired
	public CategoryJsonDeserializer(CategoryDao dao) {
		super(dao);
	}
}
