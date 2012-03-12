package sforums.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import sforums.domain.User;

public class MySqlUserDao extends AbstractDatabaseDao implements UserDao {

	public void save(User user) throws DataAccessException {
		synchronized (user) {
			try {
				Connection connection = super.getConnection();
				try {
					if (user.isIdSet()) {
						this.update(user, connection);
					} else {
						this.create(user, connection);
					}
				} finally {
					connection.close();
				}
			} catch (SQLException e) {
				String msg = e.getMessage();
				if (msg != null && msg.contains("Duplicate")) {
					throw new DuplicateIdException(user.getEmail(), e);
				}
				throw new DataAccessException("Failed to create user: " + user,
						e);
			}
		}

	}

	private void create(User user, Connection connection) throws SQLException {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		user.setCreated(now);
		PreparedStatement stmt = connection
				.prepareStatement("INSERT INTO user (firstName, lastName, organization, title, email, passwordDigest, created) VALUES (?, ?, ?, ?, ?, ?, ?)");
		try {
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getOrganization());
			stmt.setString(4, user.getTitle());
			stmt.setString(5, user.getEmail());
			stmt.setString(6, user.getPasswordDigest());
			stmt.setTimestamp(7, now);
			stmt.executeUpdate();
		} finally {
			stmt.close();
		}
		user.setId(super.getLastInsertId(connection));
		if (super.logger.isDebugEnabled()) {
			super.logger.debug("Created user: " + user + " with id "
					+ user.getId());
		}
	}

	private void update(User user, Connection connection) throws SQLException {
		boolean passwordIsSet = user.getPasswordDigest() != null;
		PreparedStatement stmt = connection
				.prepareStatement("UPDATE user SET firstName=?, lastName=?, organization=?, title=?, email=?"
						+ (passwordIsSet ? ", passwordDigest=?" : "")
						+ " WHERE id=?");
		try {
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getOrganization());
			stmt.setString(4, user.getTitle());
			stmt.setString(5, user.getEmail());
			if (passwordIsSet) {
				stmt.setString(6, user.getPasswordDigest());
				stmt.setLong(7, user.getId());
			} else {
				stmt.setLong(6, user.getId());
			}
			stmt.executeUpdate();
			if (super.logger.isDebugEnabled()) {
				super.logger.debug("Updated: " + user);
			}
		} finally {
			stmt.close();
		}
	}

	public void delete(User user) throws DataAccessException {
		this.deleteById(user.getId());
	}

	public void deleteById(Long id) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("DELETE FROM user WHERE id=?");
				try {
					stmt.setLong(1, id);
					stmt.executeUpdate();
					if (super.logger.isDebugEnabled()) {
						super.logger.debug("Deleted user with id " + id);
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to delete user by id: " + id,
					e);
		}
	}

	public List<User> getAll() throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				Statement stmt = connection.createStatement();
				try {
					ResultSet rs = stmt
							.executeQuery("SELECT id, firstName, lastName, organization, title, email, passwordDigest, created FROM user ORDER BY firstName, lastName");
					try {
						List<User> userList = new ArrayList<User>();
						while (rs.next()) {
							userList.add(this.loadFromRow(rs));
						}
						if (super.logger.isDebugEnabled()) {
							super.logger.debug("Got all users");
						}
						return userList;
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
			throw new DataAccessException("Failed to get all users", e);
		}
	}

	public User getById(Long id) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("SELECT id, firstName, lastName, organization, title, email, passwordDigest, created FROM user WHERE id=?");
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
			throw new DataAccessException("Failed to get user by id: " + id, e);
		}
	}

	public User getByEmail(String email) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("SELECT id, firstName, lastName, organization, title, email, passwordDigest, created FROM user WHERE email=?");
				try {
					stmt.setString(1, email);
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
			throw new DataAccessException("Failed to get user by email: "
					+ email, e);
		}
	}

	private User loadFromRow(ResultSet row) throws SQLException {
		User user = new User();
		user.setId(row.getLong(1));
		user.setFirstName(row.getString(2));
		user.setLastName(row.getString(3));
		user.setOrganization(row.getString(4));
		user.setTitle(row.getString(5));
		user.setEmail(row.getString(6));
		user.setPasswordDigest(row.getString(7));
		user.setCreated(row.getTimestamp(8));
		if (super.logger.isTraceEnabled()) {
			super.logger.trace("Got " + user);
		}
		return user;
	}
}
