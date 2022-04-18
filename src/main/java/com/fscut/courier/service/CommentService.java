package com.fscut.courier.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fscut.courier.model.dto.CommentDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.Comment;
import com.google.j2objc.annotations.ObjectiveCName;

import java.util.Map;

/**
 * @author lxw
 */

public interface CommentService extends IService<Comment> {
    /**
     * 评价配送员
     *
     * @param commentDTO 评价信息
     * @return
     */
    void commentSender(CommentDTO commentDTO);

    /**
     * 用户查看评价
     *
     * @param pageDTO
     * @return
     */
    Map<String, Object> userCommentDisplay(PageDTO pageDTO);

    /**
     * 用户-删除评价
     *
     * @param commentDTO 评价信息
     * @return
     */
    void userDeleteComment(CommentDTO commentDTO);

    Map<String, Object> senderCommentDisplay(PageDTO pageDTO);
}
