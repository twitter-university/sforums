
package com.marakana.sforums.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.marakana.sforums.domain.Reply;

public class HibernateReplyDao extends AbstractTimeStampedEntityHibernateDao<Reply> implements
        ReplyDao {

    @Transactional(readOnly = true)
    @Override
    public List<Reply> getAll() throws DataAccessException {
        return super.findAll("from Reply order by created");
    }
}
