
package com.marakana.sforums.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.marakana.sforums.dao.ReplyDao;
import com.marakana.sforums.dao.TopicDao;
import com.marakana.sforums.domain.Reply;
import com.marakana.sforums.domain.Topic;
import com.marakana.sforums.service.UserContextService;

@Controller
@RequestMapping("/reply_form.html")
public class ReplyFormController {
    private static final Logger logger = LoggerFactory.getLogger(ReplyFormController.class);

    private final ReplyDao replyDao;

    private final TopicDao topicDao;

    private final UserContextService userContextService;

    @Autowired
    public ReplyFormController(ReplyDao replyDao, TopicDao topicDao,
            UserContextService userContextService) {
        this.replyDao = replyDao;
        this.topicDao = topicDao;
        this.userContextService = userContextService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@RequestParam(value = "topic", required = true)
    Long topicId, @RequestParam(value = "message", required = true)
    String message) throws MissingServletRequestParameterException {
        if (message != null && !message.isEmpty()) {
            Topic topic = this.topicDao.getById(topicId);
            if (topic != null) {
                Reply reply = new Reply();
                reply.setAuthor(this.userContextService.getUserFromContext());
                reply.setTopic(topic);
                reply.setMessage(message);
                this.replyDao.save(reply);
                logger.debug("Saved reply to topic {}", topicId);
                return "redirect:topic.html?id=" + topicId + "#p" + reply.getId();
            }
        }
        logger.warn("Refusing to save reply without all of the required components");
        return "redirect:topic.html?id=" + topicId + "&error=true";
    }
}
