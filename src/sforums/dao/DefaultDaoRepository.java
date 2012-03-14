package sforums.dao;

import org.springframework.beans.factory.annotation.Required;

public class DefaultDaoRepository implements DaoRepository {

	private CategoryDao categoryDao;

	private UserDao userDao;

	@Override
	public CategoryDao getCategoryDao() {
		return this.categoryDao;
	}

	@Required
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	public UserDao getUserDao() {
		return this.userDao;
	}

	@Required
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}