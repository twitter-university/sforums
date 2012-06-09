package sforums.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import sforums.dao.CategoryDao;
import sforums.dao.ForumDao;
import sforums.dao.ReplyDao;
import sforums.dao.TopicDao;
import sforums.dao.UserDao;
import sforums.domain.Category;
import sforums.domain.CategoryList;
import sforums.domain.Forum;
import sforums.domain.ForumList;
import sforums.domain.IdentifiableEntity;
import sforums.domain.PostList;
import sforums.domain.Reply;
import sforums.domain.ReplyList;
import sforums.domain.Topic;
import sforums.domain.TopicList;
import sforums.domain.User;
import sforums.domain.UserList;

@Controller
@RequestMapping("/api_v1_0_0")
public class RestController {
	private static final Logger logger = LoggerFactory
			.getLogger(RestController.class);

	private final CategoryDao categoryDao;
	private final ForumDao forumDao;
	private final TopicDao topicDao;
	private final ReplyDao replyDao;
	private final UserDao userDao;

	@Autowired
	public RestController(CategoryDao categoryDao, ForumDao forumDao,
			TopicDao topicDao, ReplyDao replyDao, UserDao userDao) {
		this.categoryDao = categoryDao;
		this.forumDao = forumDao;
		this.topicDao = topicDao;
		this.replyDao = replyDao;
		this.userDao = userDao;
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	CategoryList getAllCategories() {
		logger.debug("Getting all categories");
		return new CategoryList(this.categoryDao.getAll());
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	Category getCategoryById(@PathVariable("id") long id) {
		logger.debug("Getting category by id: " + id);
		return checkNull(this.categoryDao.getById(id));
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCategory(@PathVariable("id") long id) {
		logger.debug("Deleting category by id: " + id);
		this.categoryDao.deleteById(id);
	}

	@RequestMapping(value = "/categories", method = RequestMethod.POST, consumes = "application/xml", produces = "application/xml")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	Category createCategory(@Valid @RequestBody Category category) {
		logger.debug("Creating category: " + category);
		this.categoryDao.save(category);
		return category;
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.PUT, consumes = "application/xml")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateCategory(@Valid @RequestBody Category category,
			@PathVariable("id") long id) {
		logger.debug("Updating category: " + category);
		this.categoryDao.save(verifyId(category, id));
	}

	@RequestMapping(value = "/categories/{id}/forums", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	ForumList getAllForumsForCategory(@PathVariable("id") long id) {
		logger.debug("Getting all forums for category " + id);
		return new ForumList(checkNull(this.categoryDao.getById(id))
				.getForums());
	}

	@RequestMapping(value = "/forums", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	ForumList getAllForums() {
		logger.debug("Getting all forums");
		return new ForumList(this.forumDao.getAll());
	}

	@RequestMapping(value = "/forums/{id}", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	Forum getForumById(@PathVariable("id") long id) {
		logger.debug("Getting forum by " + id);
		return checkNull(this.forumDao.getById(id));
	}

	@RequestMapping(value = "/forums", method = RequestMethod.POST, consumes = "application/xml", produces = "application/xml")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	Forum createForum(@Valid @RequestBody Forum forum) {
		logger.debug("Creating forum: " + forum);
		this.forumDao.save(forum);
		return forum;
	}

	@RequestMapping(value = "/forums/{id}", method = RequestMethod.PUT, consumes = "application/xml")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateForum(@Valid @RequestBody Forum forum,
			@PathVariable("id") long id) {
		logger.debug("Updating forum: " + forum);
		this.forumDao.save(verifyId(forum, id));
	}

	@RequestMapping(value = "/forums/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteForum(@PathVariable("id") long id) {
		logger.debug("Deleting forum by id: " + id);
		this.forumDao.deleteById(id);
	}

	@RequestMapping(value = "/forums/{id}/topics", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	TopicList getTopicsForForum(@PathVariable("id") long id) {
		logger.debug("Getting topics for forum by " + id);
		return new TopicList(checkNull(this.forumDao.getById(id)).getTopics());
	}

	@RequestMapping(value = "/topics", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	TopicList getTopics() {
		logger.debug("Getting topics");
		return new TopicList(this.topicDao.getAll());
	}

	@RequestMapping(value = "/topics/{id}", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	Topic getTopicById(@PathVariable("id") long id) {
		logger.debug("Getting topic by " + id);
		return checkNull(this.topicDao.getById(id));
	}

	@RequestMapping(value = "/topics", method = RequestMethod.POST, consumes = "application/xml", produces = "application/xml")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	Topic createTopic(@Valid @RequestBody Topic topic) {
		logger.debug("Creating topic: " + topic);
		this.topicDao.save(topic);
		return topic;
	}

	@RequestMapping(value = "/topics/{id}", method = RequestMethod.PUT, consumes = "application/xml")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateTopic(@Valid @RequestBody Topic topic,
			@PathVariable("id") long id) {
		logger.debug("Updating topic: " + topic);
		this.topicDao.save(verifyId(topic, id));
	}

	@RequestMapping(value = "/topics/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTopic(@PathVariable("id") long id) {
		logger.debug("Deleting topic by id: " + id);
		this.topicDao.deleteById(id);
	}

	@RequestMapping(value = "/topics/{id}/replies", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	ReplyList getRepliesForTopic(@PathVariable("id") long id) {
		logger.debug("Getting replies for topic by " + id);
		return new ReplyList(checkNull(this.topicDao.getById(id)).getReplies());
	}

	@RequestMapping(value = "/replies/{id}", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	Reply getReplyById(@PathVariable("id") long id) {
		logger.debug("Getting reply by " + id);
		return checkNull(this.replyDao.getById(id));
	}

	@RequestMapping(value = "/replies", method = RequestMethod.POST, consumes = "application/xml", produces = "application/xml")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	Reply createReply(@Valid @RequestBody Reply reply) {
		logger.debug("Creating reply: " + reply);
		this.replyDao.save(reply);
		return reply;
	}

	@RequestMapping(value = "/replies/{id}", method = RequestMethod.PUT, consumes = "application/xml")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateReply(@Valid @RequestBody Reply reply,
			@PathVariable("id") long id) {
		logger.debug("Updating reply: " + reply);
		this.replyDao.save(verifyId(reply, id));
	}

	@RequestMapping(value = "/replies/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteReply(@PathVariable("id") long id) {
		logger.debug("Deleting reply by id: " + id);
		this.replyDao.deleteById(id);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	UserList getAllUsers() {
		logger.debug("Getting all users");
		return new UserList(this.userDao.getAll());
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	User getUserById(@PathVariable("id") long id) {
		logger.debug("Getting user by " + id);
		return checkNull(this.userDao.getById(id));
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST, consumes = "application/xml", produces = "application/xml")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	User createUsery(@Valid @RequestBody User user) {
		logger.debug("Creating user: " + user);
		this.userDao.save(user);
		return user;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT, consumes = "application/xml")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUser(@Valid @RequestBody User user,
			@PathVariable("id") long id) {
		logger.debug("Updating user: " + user);
		this.userDao.save(verifyId(user, id));
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") long id) {
		logger.debug("Deleting user by id: " + id);
		this.userDao.deleteById(id);
	}

	@RequestMapping(value = "/users/{id}/posts", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	PostList getTopicsForUser(@PathVariable("id") long id) {
		logger.debug("Getting posts for user by id " + id);
		return new PostList(checkNull(this.userDao.getById(id)).getPosts());
	}

	private <E> E checkNull(E e) {
		if (e == null) {
			// sets 404 on the response
			throw new NoSuchResourceException("No such entity");
		}
		return e;
	}

	private <E extends IdentifiableEntity> E verifyId(E e, long id) {
		if (e == null) {
			// sets 404 on the response
			throw new NoSuchResourceException("No such entity");
		} else if (e.getId() == null) {
			e.setId(id);
		} else if (e.getId().longValue() != id) {
			throw new IllegalArgumentException("Invalid ID [" + id + "] on ["
					+ e + "] with ID [" + e.getId() + "]");
		}
		return e;
	}

	@ExceptionHandler(Exception.class)
	public void handleException(Exception e, HttpServletResponse response)
			throws IOException {
		int code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		String error = "Internal Server Error";
		if (e instanceof ConcurrencyFailureException) {
			code = HttpServletResponse.SC_CONFLICT;
			error = "Concurrent modification failure";
		} else if (e instanceof ConstraintViolationException) {
			code = HttpServletResponse.SC_BAD_REQUEST;
			error = "Constraint violation failure";
		} else if (e instanceof DataIntegrityViolationException) {
			code = HttpServletResponse.SC_CONFLICT;
			error = "Data integrity violation failure";
		} else if (e instanceof NoSuchResourceException) {
			code = HttpServletResponse.SC_NOT_FOUND;
			error = "No such resource";
		} else if (e instanceof DataRetrievalFailureException) {
			code = HttpServletResponse.SC_NOT_FOUND;
			error = "No such resource";
		} else if (e instanceof IllegalArgumentException) {
			code = HttpServletResponse.SC_BAD_REQUEST;
			error = "Invalid request";
		}
		if (logger.isWarnEnabled()) {
			logger.warn("Encountered an exception. Sending code=" + code
					+ ", error=" + error, e);
		}
		response.sendError(code, error);
	}
}