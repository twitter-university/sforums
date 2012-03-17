package sforums.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sforums.dao.ReplyDao;
import sforums.dao.TopicDao;
import sforums.domain.Reply;
import sforums.service.UserContextService;

@Controller
@RequestMapping("/reply_form.html")
public class ReplyFormController {
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
    public String processSubmit(
            @RequestParam(value = "topic", required = true) Long topicId,
            @RequestParam(value = "message", required = true) String message) {
        Reply reply = new Reply();
        if (message != null && !message.isEmpty()) {
            reply.setAuthor(this.userContextService.getUserFromContext());
            reply.setTopic(this.topicDao.getById(topicId));
            reply.setMessage(message);
            this.replyDao.save(reply);
        }
        return "redirect:topic.html?id=" + topicId + "#reply" + reply.getId();
    }
}