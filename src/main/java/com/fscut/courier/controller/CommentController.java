package com.fscut.courier.controller;

import com.fscut.courier.model.dto.CommentDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.service.CommentService;
import com.fscut.courier.service.CommonService;
import com.fscut.courier.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

import static com.fscut.courier.utils.ResultUtil.ok;

/**
 * @author lxw
 */
@Validated
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    /**
     * 用户-评价配送员
     *
     * @param commentDTO 评价信息
     * @return
     */
    @PostMapping("sender")
    public ResultUtil.Result commentSender(@RequestBody @Validated(CommentDTO.Save.class) CommentDTO commentDTO) {
        commentService.commentSender(commentDTO);
        return ok();
    }

    /**
     * 用户-查看评价
     *
     * @param pageDTO
     * @return
     */
    @PostMapping("user/display")
    public ResultUtil.Result userCommentDisplay(@RequestBody @Validated(PageDTO.UserComment.class) PageDTO pageDTO) {
        return ok(commentService.userCommentDisplay(pageDTO));
    }

    /**
     * 用户-删除评价
     *
     * @param commentDTO 评价信息
     * @return
     */
    @GetMapping("user/delete")
    public ResultUtil.Result userDeleteComment(@RequestBody @Validated(CommentDTO.UserDelete.class) CommentDTO commentDTO) {
        commentService.userDeleteComment(commentDTO);
        return ok();
    }

    @PostMapping("sender/display")
    public ResultUtil.Result senderCommentDisplay(@RequestBody @Validated(PageDTO.UserComment.class) PageDTO pageDTO) {
        return ok(commentService.senderCommentDisplay(pageDTO));
    }

}
