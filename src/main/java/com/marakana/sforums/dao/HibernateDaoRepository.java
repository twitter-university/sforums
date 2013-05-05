
package com.marakana.sforums.dao;

public class HibernateDaoRepository implements DaoRepository {

    private CategoryDao categoryDao = new HibernateCategoryDao();

    private UserDao userDao = new HibernateUserDao();

    @Override
    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    @Override
    public UserDao getUserDao() {
        return this.userDao;
    }
}
