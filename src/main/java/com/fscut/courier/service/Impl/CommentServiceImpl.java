package com.fscut.courier.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fscut.courier.dao.CommentDao;
import com.fscut.courier.model.po.Comment;
import com.fscut.courier.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * @author lxw
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
}
