package sforums.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import sforums.Util;
import sforums.dao.UserDao;
import sforums.domain.User;

@Controller
@RequestMapping("/user_form.html")
@SessionAttributes("userAndPassword")
public class UserAndPasswordFormController {
	private final UserDao dao;

	@Autowired
	public UserAndPasswordFormController(UserDao dao) {
		this.dao = dao;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			@RequestParam(value = "id", required = false) Long id) {
		User user = id == null ? new User() : this.dao.getById(id);
		return new ModelAndView("userForm")
				.addObject(new UserAndPassword(user));
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute("userAndPassword") @Valid UserAndPassword userAndPassword,
			BindingResult result, SessionStatus status) {
		if (!result.hasErrors()) {
			try {
				User user = userAndPassword.getUser();
				if (userAndPassword.isPasswordVerified()) {
					user.setPasswordDigest(Util.md5Digest(userAndPassword
							.getPassword()));
				}
				this.dao.save(user);
				status.setComplete();
				return "redirect:user.html?id=" + user.getId();
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("user.email", "DuplicateEmailFailure");
			} catch (ConcurrencyFailureException e) {
				result.reject("ConcurrentModificatonFailure");
			}
		}
		return "userForm";
	}
}