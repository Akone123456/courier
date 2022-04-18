package com.fscut.courier.model.vo.factory;

import com.fscut.courier.model.po.Comment;
import com.fscut.courier.model.vo.CommentVO;

/**
 * @author lxw
 */
public class CommentVOFactory {
    private CommentVOFactory() {

    }

    public static CommentVO createUserCommentVO(Comment comment, String senderUserName) {
        return CommentVO.builder()
                .commentId(comment.getId())
                .orderId(comment.getOrderId())
                .senderUserName(senderUserName)
                .evaluation(comment.getEvaluation())
                .commentNote(comment.getCommentNote())
                .build();
    }

    public static CommentVO createSenderCommentVO(Comment comment) {
        return CommentVO.builder()
                .commentId(comment.getId())
                .orderId(comment.getOrderId())
                .evaluation(comment.getEvaluation())
                .commentNote(comment.getCommentNote())
                .build();
    }

}
