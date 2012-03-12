package sforums.dao;

public class MySqlDaoRepository implements DaoRepository {

    private CategoryDao categoryDao = new MySqlCategoryDao();

    private UserDao userDao = new MySqlUserDao();

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public UserDao getUserDao() {
        return this.userDao;
    }
}
