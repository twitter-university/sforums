package sforums.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractDatabaseDao {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private String jndiName = "java:comp/env/jdbc/sforums";

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    protected Connection getConnection() throws DataAccessException {
        try {
            Context ctx = new InitialContext();
            try {
                DataSource ds = (DataSource) ctx.lookup(this.jndiName);
                Connection connection = ds.getConnection();
                if (logger.isTraceEnabled()) {
                    logger.trace("Got connection");
                }
                return connection;
            } finally {
                ctx.close();
            }
        } catch (NamingException e) {
            throw new DataAccessException(
                    "Failed to lookup connection from JNDI by name: "
                            + this.jndiName, e);
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Failed to get connection from DataSource: "
                            + this.jndiName, e);
        }
    }

    protected String getLastInsertIdSql() {
        return "SELECT @@IDENTITY";
    }

    protected Long getLastInsertId(Connection connection)
            throws DataAccessException {
        try {
            Statement stmt = connection.createStatement();
            try {
                ResultSet rs = stmt.executeQuery(this.getLastInsertIdSql());
                try {
                    if (rs.next()) {
                        return rs.getLong(1);
                    } else {
                        throw new DataAccessException(
                                "Last insert key id is missing");
                    }
                } finally {
                    rs.close();
                }
            } finally {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to get last insert id", e);
        }
    }
}
