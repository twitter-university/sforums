package sforums.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sforums.domain.Category;

public class MySqlCategoryDao extends AbstractDatabaseDao implements
		CategoryDao {

	public void save(Category category) throws DataAccessException {
		synchronized (category) {
			try {
				Connection connection = super.getConnection();
				try {
					if (category.isIdSet()) {
						this.update(category, connection);
					} else {
						this.create(category, connection);
					}
				} finally {
					connection.close();
				}
			} catch (SQLException e) {
				String msg = e.getMessage();
				if (msg != null && msg.contains("Duplicate")) {
					throw new DuplicateIdException(category.getName(), e);
				}
				throw new DataAccessException("Failed to create: " + category,
						e);
			}
		}
	}

	private void create(Category category, Connection connection)
			throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("INSERT INTO category (name, description) VALUES (?, ?)");
		try {
			stmt.setString(1, category.getName());
			stmt.setString(2, category.getDescription());
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
		category.setId(super.getLastInsertId(connection));
		if (super.logger.isDebugEnabled()) {
			super.logger.debug("Created: " + category + " with id "
					+ category.getId());
		}
	}

	private void update(Category category, Connection connection)
			throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("UPDATE category SET name=?, description=? WHERE id=?");
		try {
			stmt.setString(1, category.getName());
			stmt.setString(2, category.getDescription());
			stmt.setLong(3, category.getId());
			stmt.executeUpdate();
			if (super.logger.isDebugEnabled()) {
				super.logger.debug("Updated: " + category);
			}
		} finally {
			stmt.close();
		}
	}

	public void delete(Category category) throws DataAccessException {
		this.deleteById(category.getId());
	}

	public void deleteById(Long id) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("DELETE FROM category WHERE id=?");
				try {
					stmt.setLong(1, id);
					stmt.executeUpdate();
					if (super.logger.isDebugEnabled()) {
						super.logger.debug("Deleted category with id " + id);
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to delete category by id: "
					+ id, e);
		}
	}

	public List<Category> getAll() throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				Statement stmt = connection.createStatement();
				try {
					ResultSet rs = stmt
							.executeQuery("SELECT id, name, description FROM category ORDER BY name");
					try {
						List<Category> categoryList = new ArrayList<Category>();
						while (rs.next()) {
							categoryList.add(this.loadFromRow(rs));
						}
						if (super.logger.isDebugEnabled()) {
							super.logger.debug("Got all categories");
						}
						return categoryList;
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to get all categories", e);
		}
	}

	public Category getById(Long id) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("SELECT id, name, description FROM category WHERE id=?");
				try {
					stmt.setLong(1, id);
					ResultSet rs = stmt.executeQuery();
					try {
						return rs.next() ? this.loadFromRow(rs) : null;
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException(
					"Failed to get category by id: " + id, e);
		}
	}

	public Category getByName(String name) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("SELECT id, name, description FROM category WHERE name LIKE ?");
				try {
					stmt.setString(1, name);
					ResultSet rs = stmt.executeQuery();
					try {
						return rs.next() ? this.loadFromRow(rs) : null;
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to get category by name: "
					+ name, e);
		}
	}

	private Category loadFromRow(ResultSet row) throws SQLException {
		Category category = new Category();
		category.setId(row.getLong(1));
		category.setName(row.getString(2));
		category.setDescription(row.getString(3));
		if (super.logger.isTraceEnabled()) {
			super.logger.trace("Got " + category);
		}
		return category;
	}
}
