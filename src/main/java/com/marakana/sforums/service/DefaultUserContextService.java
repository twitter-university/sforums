
package com.marakana.sforums.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.marakana.sforums.domain.User;
import com.marakana.sforums.security.DefaultUserDetails;

public class DefaultUserContextService implements UserContextService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultUserContextService.class);

    @Override
    public User getUserFromContext() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null) {
            logger.trace("Cannot get authenticated contact. Security context is not initialized.");
            return null;
        }
        Authentication auth = ctx.getAuthentication();
        if (auth == null) {
            logger.trace("Cannot get authenticated contact. Authentication info is not present.");
            return null;
        }
        Object principal = auth.getPrincipal();
        if (principal == null) {
            logger.trace("Cannot get authenticated contact. Principal info is not present.");
            return null;
        }
        if (principal instanceof DefaultUserDetails) {
            return ((DefaultUserDetails) principal).getUser();
        } else if (principal instanceof String) {
            logger.trace("Not authenticated: {}", principal);
        } else {
            logger.debug("Cannot get authenticated contact from authenticated principal: {}",
                    principal);
        }
        return null;
    }

    @Override
    public void addUserToContext(User user, String password) {
        User authUser = this.getUserFromContext();
        if (authUser == null) {
            if (user == null) {
                throw new NullPointerException("User must not be null");
            } else if (user.isEnabled()) {
                logger.trace("Logging in {}", user);
                DefaultUserDetails userDetails = new DefaultUserDetails(user);
                SecurityContext ctx = SecurityContextHolder.getContext();
                ctx.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails,
                        password, userDetails.getAuthorities()));
                logger.debug("Logged in {}", user);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("Not logging in disabled user: " + user);
                }
            }
        } else if (authUser.equals(user)) {
            logger.debug("User {} already logged in", user);
        } else {
            throw new IllegalStateException("Already logged in as another user: " + user);
        }
    }
}
