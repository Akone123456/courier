package com.fscut.courier.controller;

import com.fscut.courier.model.dto.CommentDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.service.CommonService;
import com.fscut.courier.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fscut.courier.utils.ResultUtil.ok;

/**
 * @author lxw
 */
@Validated
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommonService commonService;

    @PostMapping("user/display")
    public ResultUtil.Result userCommetDisplay(@RequestBody @Validated(PageDTO.UserComment.class) PageDTO pageDTO) {
        return ok();
    }

    /**
     * 评价配送员
     *
     * @param commentDTO 评价信息
     * @return
     */
    @PostMapping("sender")
    public ResultUtil.Result commentSender(@RequestBody @Validated(CommentDTO.Save.class) CommentDTO commentDTO){
        commonService.commentSender(commentDTO);
        return ok();
    }

}
