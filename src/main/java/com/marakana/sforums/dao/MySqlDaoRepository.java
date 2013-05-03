package com.marakana.sforums.dao;

public class MySqlDaoRepository implements DaoRepository {

    private CategoryDao categoryDao = new MySqlCategoryDao();

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

}
