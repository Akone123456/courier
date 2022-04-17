package com.fscut.courier.model.vo.factory;

import com.fscut.courier.model.po.Comment;
import com.fscut.courier.model.vo.CommentVO;

/**
 * @author lxw
 */
public class CommentVOFactory {
    private CommentVOFactory() {

    }

    public static CommentVO createUserCommentVO(Comment comment, String senderRealName) {
        return CommentVO.builder()
                .commentId(comment.getId())
                .senderRealName(senderRealName)
                .evaluation(comment.getEvaluation())
                .commentNote(comment.getCommentNote())
                .build();
    }

}
