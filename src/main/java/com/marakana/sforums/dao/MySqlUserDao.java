
package com.marakana.sforums.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.marakana.sforums.domain.User;

public class MySqlUserDao extends AbstractDatabaseDao implements UserDao {
    @Override
    public void save(User user) throws DataAccessException {
        synchronized (user) {
            try (Connection connection = super.getConnection()) {
                if (user.isIdSet()) {
                    this.update(user, connection);
                } else {
                    this.create(user, connection);
                }
            } catch (SQLException e) {
                String msg = e.getMessage();
                if (msg != null && msg.contains("Duplicate")) {
                    throw new DuplicateIdException(user.getEmail(), e);
                }
                throw new DataAccessException("Failed to create user: " + user, e);
            }
        }

    }

    private void create(User user, Connection connection) throws SQLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        user.setCreated(now);
        final String sql = "INSERT INTO user (firstName, lastName, organization, title, email, passwordDigest, created) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getOrganization());
            stmt.setString(4, user.getTitle());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getPasswordDigest());
            stmt.setTimestamp(7, now);
            stmt.executeUpdate();
        }
        user.setId(super.getLastInsertId(connection));
        super.logger.debug("Created {} with id {}", user, user.getId());
    }

    private void update(User user, Connection connection) throws SQLException {
        // Why do we have special handling for the password digest?
        // Why is this bad?
        final boolean passwordDigestIsSet = user.getPasswordDigest() != null;
        final String sql = "UPDATE user SET firstName=?, lastName=?, organization=?, title=?, email=?"
                + (passwordDigestIsSet ? ", passwordDigest=?" : "") + " WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getOrganization());
            stmt.setString(4, user.getTitle());
            stmt.setString(5, user.getEmail());
            if (passwordDigestIsSet) {
                stmt.setString(6, user.getPasswordDigest());
                stmt.setLong(7, user.getId());
            } else {
                stmt.setLong(6, user.getId());
            }
            stmt.executeUpdate();
            super.logger.debug("Updated {}", user);
        }
    }

    @Override
    public void delete(User user) throws DataAccessException {
        try (Connection connection = super.getConnection()) {
            final String sql = "DELETE FROM user WHERE id=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, user.getId());
                stmt.executeUpdate();
                super.logger.debug("Deleted {}", user);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to delete user: " + user, e);
        }
    }

    @Override
    public List<User> getAll() throws DataAccessException {
        try (Connection connection = super.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                final String sql = "SELECT id, firstName, lastName, organization, title, email, passwordDigest, created FROM user ORDER BY firstName, lastName";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    List<User> userList = new ArrayList<User>();
                    while (rs.next()) {
                        userList.add(this.loadFromRow(rs));
                    }
                    super.logger.debug("Got {} users", userList.size());
                    return userList;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to get all users", e);
        }
    }

    @Override
    public User getById(Long id) throws DataAccessException {
        try (Connection connection = super.getConnection()) {
            final String sql = "SELECT id, firstName, lastName, organization, title, email, passwordDigest, created FROM user WHERE id=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    User user = rs.next() ? this.loadFromRow(rs) : null;
                    super.logger.debug("Got {} by id {}", user, id);
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to get user by id: " + id, e);
        }
    }

    @Override
    public User getByEmail(String email) throws DataAccessException {
        try (Connection connection = super.getConnection()) {
            final String sql = "SELECT id, firstName, lastName, organization, title, email, passwordDigest, created FROM user WHERE email=?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    User user = rs.next() ? this.loadFromRow(rs) : null;
                    super.logger.debug("Got {} by email {}", user, email);
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to get user by email: " + email, e);
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
        return user;
    }
}
