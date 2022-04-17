package com.fscut.courier.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fscut.courier.model.po.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lxw
 */
@Repository
public interface CommentDao extends BaseMapper<Comment> {
}
