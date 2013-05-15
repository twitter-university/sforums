
package com.marakana.sforums.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.marakana.sforums.domain.User;
import com.marakana.sforums.service.UserContextService;
import com.marakana.sforums.service.UserStoreService;

@Controller
@RequestMapping({
        "/user_profile.html", "/user_register.html"
})
@SessionAttributes("userAndPassword")
public class UserAndPasswordProfileFormController {
    private static final Logger logger = LoggerFactory
            .getLogger(UserAndPasswordProfileFormController.class);

    private final UserContextService userContextService;

    private final UserStoreService userStoreService;

    @Autowired
    public UserAndPasswordProfileFormController(UserContextService userContextService,
            UserStoreService userStoreService) {
        this.userContextService = userContextService;
        this.userStoreService = userStoreService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ModelAttribute("userAndPassword")
    public ModelAndView setupForm() {
        User user = this.userContextService.getUserFromContext();
        if (user == null) {
            logger.debug("Setting up form for new user");
            user = new User();
        } else {
            logger.debug("Setting up form for {}", user);
        }
        return new ModelAndView("userForm").addObject(new UserAndPassword(user)).addObject(
                "profile", Boolean.TRUE);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("user.enabled", "user.admin", "user.passwordDigest");
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("userAndPassword")
    @Valid
    UserAndPassword userAndPassword, BindingResult result, SessionStatus status) {
        if (!result.hasErrors()) {
            try {
                User user = userAndPassword.getUser();
                logger.debug("Processing submit for {}", user);
                String password = null;
                if (userAndPassword.isPasswordVerified()) {
                    password = userAndPassword.getPassword();
                }
                logger.debug("Storing {}", user);
                this.userStoreService.store(user, password);
                status.setComplete();
                logger.debug("Logging in {}", user);
                this.userContextService.addUserToContext(user, password);
                logger.debug("Done {}", user);
                return "redirect:user_profile.html?success=true";
            } catch (DataIntegrityViolationException e) {
                result.rejectValue("user.email", "DuplicateEmailFailure");
            } catch (ConcurrencyFailureException e) {
                result.reject("ConcurrentModificatonFailure");
            }
        }
        return "userForm";
    }
}
