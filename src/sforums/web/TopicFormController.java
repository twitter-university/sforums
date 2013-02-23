package sforums.web;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import sforums.dao.ForumDao;
import sforums.dao.TopicDao;
import sforums.domain.Forum;
import sforums.domain.Topic;
import sforums.service.UserContextService;

@Controller
@RequestMapping("/topic_form.html")
@SessionAttributes("topic")
public class TopicFormController {
	private final ForumDao forumDao;
	private final TopicDao topicDao;
	private final UserContextService userContextService;

	@Autowired
	public TopicFormController(TopicDao topicDao, ForumDao forumDao,
			UserContextService userContextService) {
		this.topicDao = topicDao;
		this.forumDao = forumDao;
		this.userContextService = userContextService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Forum.class, new IdentifiableEntityEditor(
				this.forumDao));
		binder.setAllowedFields(new String[] { "forum", "title", "message" });
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "forumId", required = false) Long forumId) {
		Topic topic;
		if (id == null) {
			topic = new Topic();
			if (forumId != null) {
				topic.setForum(this.forumDao.getById(forumId));
			}
			topic.setAuthor(this.userContextService.getUserFromContext());
		} else {
			topic = this.topicDao.getById(id);
		}
		return new ModelAndView("topicForm").addObject(topic);
	}

	@ModelAttribute("forumList")
	public List<Forum> addForumListToModel() {
		return this.forumDao.getAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("topic") @Valid Topic topic,
			BindingResult result, SessionStatus status) {
		if (!result.hasErrors()) {
			try {
				this.topicDao.save(topic);
				status.setComplete();
				return "redirect:topic.html?id=" + topic.getId();
			} catch (DataIntegrityViolationException e) {
				result.rejectValue("title", "DuplicateTitleFailure");
			} catch (ConcurrencyFailureException e) {
				result.reject("ConcurrentModificatonFailure",
						new String[] { "topic" }, null);
			}
		}
		return "topicForm";
	}
}