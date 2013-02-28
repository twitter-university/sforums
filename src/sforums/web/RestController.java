package sforums.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
import sforums.domain.Forum;
import sforums.domain.IdentifiableEntity;
import sforums.domain.Post;
import sforums.domain.Reply;
import sforums.domain.Topic;
import sforums.domain.User;
import sforums.xml.CategoryList;
import sforums.xml.ForumList;
import sforums.xml.PostList;
import sforums.xml.ReplyList;
import sforums.xml.TopicList;
import sforums.xml.UserList;

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
	CategoryList getAllCategoriesAsXml() {
		return new CategoryList(this.getAllCategories());
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public @ResponseBody
	List<Category> getAllCategories() {
		logger.debug("Getting all categories");
		return this.categoryDao.getAll();
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
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

	@RequestMapping(value = "/categories", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	Category createCategory(@Valid @RequestBody Category category) {
		logger.debug("Creating category: " + category);
		this.categoryDao.save(category);
		return category;
	}

	@RequestMapping(value = "/categories/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateCategory(@Valid @RequestBody Category category,
			@PathVariable("id") long id) {
		logger.debug("Updating category: " + category);
		this.categoryDao.save(verifyId(category, id));
	}

	@RequestMapping(value = "/categories/{id}/forums", method = RequestMethod.GET)
	public @ResponseBody
	List<Forum> getAllForumsForCategory(@PathVariable("id") long id) {
		logger.debug("Getting all forums for category " + id);
		return checkNull(this.categoryDao.getById(id)).getForums();
	}

	@RequestMapping(value = "/categories/{id}/forums", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	ForumList getAllForumsForCategoryAsXml(@PathVariable("id") long id) {
		return new ForumList(this.getAllForumsForCategory(id));
	}

	@RequestMapping(value = "/forums", method = RequestMethod.GET)
	public @ResponseBody
	List<Forum> getAllForums() {
		logger.debug("Getting all forums");
		return this.forumDao.getAll();
	}

	@RequestMapping(value = "/forums", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	ForumList getAllForumsAsXml() {
		return new ForumList(this.getAllForums());
	}

	@RequestMapping(value = "/forums/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Forum getForumById(@PathVariable("id") long id) {
		logger.debug("Getting forum by " + id);
		return checkNull(this.forumDao.getById(id));
	}

	@RequestMapping(value = "/forums", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	Forum createForum(@Valid @RequestBody Forum forum) {
		logger.debug("Creating forum: " + forum);
		this.forumDao.save(forum);
		return forum;
	}

	@RequestMapping(value = "/forums/{id}", method = RequestMethod.PUT)
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

	@RequestMapping(value = "/forums/{id}/topics", method = RequestMethod.GET)
	public @ResponseBody
	List<Topic> getTopicsForForum(@PathVariable("id") long id) {
		logger.debug("Getting topics for forum by " + id);
		return checkNull(this.forumDao.getById(id)).getTopics();
	}

	@RequestMapping(value = "/forums/{id}/topics", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	TopicList getTopicsForForumAsXml(@PathVariable("id") long id) {
		return new TopicList(this.getTopicsForForum(id));
	}

	@RequestMapping(value = "/topics", method = RequestMethod.GET)
	public @ResponseBody
	List<Topic> getTopics() {
		logger.debug("Getting topics");
		return this.topicDao.getAll();
	}

	@RequestMapping(value = "/topics", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	TopicList getTopicsAsXml() {
		return new TopicList(this.getTopics());
	}

	@RequestMapping(value = "/topics/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Topic getTopicById(@PathVariable("id") long id) {
		logger.debug("Getting topic by " + id);
		return checkNull(this.topicDao.getById(id));
	}

	@RequestMapping(value = "/topics", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	Topic createTopic(@Valid @RequestBody Topic topic) {
		logger.debug("Creating topic: " + topic);
		this.topicDao.save(topic);
		return topic;
	}

	@RequestMapping(value = "/topics/{id}", method = RequestMethod.PUT)
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

	@RequestMapping(value = "/topics/{id}/replies", method = RequestMethod.GET)
	public @ResponseBody
	List<Reply> getRepliesForTopic(@PathVariable("id") long id) {
		logger.debug("Getting replies for topic by " + id);
		return checkNull(this.topicDao.getById(id)).getReplies();
	}

	@RequestMapping(value = "/topics/{id}/replies", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	ReplyList getRepliesForTopicAsXml(@PathVariable("id") long id) {
		return new ReplyList(this.getRepliesForTopic(id));
	}

	@RequestMapping(value = "/replies/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Reply getReplyById(@PathVariable("id") long id) {
		logger.debug("Getting reply by " + id);
		return checkNull(this.replyDao.getById(id));
	}

	@RequestMapping(value = "/replies", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	Reply createReply(@Valid @RequestBody Reply reply) {
		logger.debug("Creating reply: " + reply);
		this.replyDao.save(reply);
		return reply;
	}

	@RequestMapping(value = "/replies/{id}", method = RequestMethod.PUT)
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

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody
	List<User> getAllUsers() {
		logger.debug("Getting all users");
		return this.userDao.getAll();
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	UserList getAllUsersAsXml() {
		return new UserList(this.getAllUsers());
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public @ResponseBody
	User getUserById(@PathVariable("id") long id) {
		logger.debug("Getting user by " + id);
		return checkNull(this.userDao.getById(id));
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	User createUsery(@Valid @RequestBody User user) {
		logger.debug("Creating user: " + user);
		this.userDao.save(user);
		return user;
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
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

	@RequestMapping(value = "/users/{id}/posts", method = RequestMethod.GET)
	public @ResponseBody
	List<Post> getTopicsForUser(@PathVariable("id") long id) {
		logger.debug("Getting posts for user by id " + id);
		return checkNull(this.userDao.getById(id)).getPosts();
	}

	@RequestMapping(value = "/users/{id}/posts", method = RequestMethod.GET, produces = "application/xml")
	public @ResponseBody
	PostList getTopicsForUserAsXml(@PathVariable("id") long id) {
		return new PostList(getTopicsForUser(id));
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

	@ExceptionHandler({ NonTransientDataAccessException.class })
	public void handleDataError(Exception e, HttpServletResponse response)
			throws IOException {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class,
			ConcurrencyFailureException.class })
	public void handleConflict(Exception e, HttpServletResponse response)
			throws IOException {
		response.setStatus(HttpServletResponse.SC_CONFLICT);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody
	List<ObjectError> handleValidation(MethodArgumentNotValidException e,
			HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return e.getBindingResult().getAllErrors();
	}
}