
package com.marakana.sforums.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.marakana.sforums.domain.User;
import com.marakana.sforums.security.DefaultUserDetails;

public class DefaultUserContextService implements UserContextService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultUserContextService.class);

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
}
