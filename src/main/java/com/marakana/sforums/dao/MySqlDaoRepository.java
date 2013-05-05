
package com.marakana.sforums.dao;

public class MySqlDaoRepository implements DaoRepository {

    private final CategoryDao categoryDao = new MySqlCategoryDao();

    private final UserDao userDao = new MySqlUserDao();

    @Override
    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    @Override
    public UserDao getUserDao() {
        return this.userDao;
    }
}
