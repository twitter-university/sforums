package sforums.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import sforums.domain.User;
import sforums.security.DefaultUserDetails;

public class DefaultUserContextService implements UserContextService {

	private static final Log logger = LogFactory
			.getLog(DefaultUserContextService.class);

	public User getUserFromContext() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		if (ctx == null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Cannot get authenticated contact. Security context is not initialized.");
			}
			return null;
		}
		Authentication auth = ctx.getAuthentication();
		if (auth == null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Cannot get authenticated contact. Authentication info is not present.");
			}
			return null;
		}
		Object principal = auth.getPrincipal();
		if (principal == null) {
			if (logger.isTraceEnabled()) {
				logger.trace("Cannot get authenticated contact. Principal info is not present.");
			}
			return null;
		}
		if (principal instanceof DefaultUserDetails) {
			return ((DefaultUserDetails) principal).getUser();
		} else if (principal instanceof String) {
			if (logger.isTraceEnabled()) {
				logger.trace("Not authenticated: [" + principal + "]");
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Cannot get authenticated contact from authenticated principal ["
						+ principal + "]");
			}
		}
		return null;
	}

	public void addUserToContext(User user, String password) {
		User authUser = this.getUserFromContext();
		if (authUser == null) {
			if (user == null) {
				throw new NullPointerException("User must not be null");
			} else if (user.isEnabled()) {
				if (logger.isTraceEnabled()) {
					logger.trace("Logging in: " + user);
				}
				DefaultUserDetails userDetails = new DefaultUserDetails(user);
				SecurityContext ctx = SecurityContextHolder.getContext();
				ctx.setAuthentication(new UsernamePasswordAuthenticationToken(
						userDetails, password, userDetails.getAuthorities()));
				if (logger.isDebugEnabled()) {
					logger.debug("Logged in: " + user);
				}
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Not logging in disabled user: " + user);
				}
			}
		} else if (authUser.equals(user)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Already logged in: " + user);
			}
		} else {
			throw new IllegalStateException(
					"Already logged in as another user: " + user);
		}
	}
}