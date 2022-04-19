package com.fscut.courier.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fscut.courier.dao.*;
import com.fscut.courier.model.dto.CommentDTO;
import com.fscut.courier.model.dto.PageDTO;
import com.fscut.courier.model.po.*;
import com.fscut.courier.model.vo.CommentVO;
import com.fscut.courier.model.vo.factory.CommentVOFactory;
import com.fscut.courier.service.CommentService;
import com.fscut.courier.service.CommonService;
import com.fscut.courier.utils.ImageUtils;
import com.google.common.collect.ImmutableMap;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.krb5.internal.PAForUserEnc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fscut.courier.utils.ConstValue.*;
import static java.util.stream.Collectors.toList;

/**
 * @author lxw
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserOrderSenderDao userOrderSenderDao;
    @Autowired
    private SenderDao senderDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private AdminDao adminDao;

    /**
     * 评价配送员
     *
     * @param commentDTO 评价信息
     * @return
     */
    @Override
    public void commentSender(CommentDTO commentDTO) {
        // 添加评价信息
        Comment comment = new Comment();
        comment.setOrderId(commentDTO.getOrderId());
        comment.setEvaluation(commentDTO.getEvaluation());
        comment.setCommentNote(commentDTO.getCommentNote());
        commentDao.insert(comment);
        // 添加日志
        UserInfo userInfo = userInfoDao.selectById(commentDTO.getUserId());
        String content = "普通用户:" + userInfo.getUsername() + ",评价该订单。" + "评价内容:" + commentDTO.getCommentNote();
        commonService.recordLog(commentDTO.getOrderId(), content);
    }

    /**
     * 用户查看评价
     *
     * @param pageDTO
     * @return
     */
    @Override
    public Map<String, Object> userCommentDisplay(PageDTO pageDTO) {
        // 查询用户未删除的订单
        QueryWrapper<UserOrderSender> userOrderSenderWrapper = new QueryWrapper<>();
        userOrderSenderWrapper.eq("user_id", pageDTO.getUserId())
                .eq("user_is_deleted", NOT_DELETED);
        List<String> orderIdList = userOrderSenderDao.selectList(userOrderSenderWrapper).stream().map(UserOrderSender::getOrderId).collect(toList());

        // 封装数据
        List<CommentVO> commentVOList = new ArrayList<>();
        if (ObjectUtils.isNotNull(orderIdList)) {
            LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
            commentWrapper.eq(ObjectUtils.isNotNull(pageDTO.getEvaluation()), Comment::getEvaluation, pageDTO.getEvaluation())
                    .between(ObjectUtils.isNotNull(pageDTO.getStartTime()) &&
                            ObjectUtils.isNotNull(pageDTO.getEndTime()), Comment::getCreateTime, pageDTO.getStartTime(), pageDTO.getEndTime())
                    .in(Comment::getOrderId, orderIdList);
            // 分页
            Page<Comment> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
            Page<Comment> commentPage = commentDao.selectPage(page, commentWrapper);

            commentPage.getRecords().forEach(comment -> {
                // 查询配送员账号
                String senderUserName = senderDao.selectSenderName(comment.getOrderId());
                CommentVO commentVO = CommentVOFactory.createUserCommentVO(comment, senderUserName);
                commentVOList.add(commentVO);
            });
            return ImmutableMap.<String, Object>builder()
                    .put(PAGE_TOTAL, commentPage.getTotal())
                    .put(COMMENT_LIST, commentVOList)
                    .build();
        }
        return ImmutableMap.<String, Object>builder()
                .put(PAGE_TOTAL, NO_ONE)
                .put(COMMENT_LIST, commentVOList)
                .build();
    }

    /**
     * 用户-删除评价
     *
     * @param commentDTO 评价信息
     * @return
     */
    @Override
    public void userDeleteComment(CommentDTO commentDTO) {
        // 删除评价
        commentDao.deleteById(commentDTO.getCommentId());
        // 添加日志
        UserInfo userInfo = userInfoDao.selectById(commentDTO.getUserId());
        String content = "普通用户:" + userInfo.getUsername() + ",删除评价";
        commonService.recordLog(commentDTO.getOrderId(), content);
    }

    /**
     * 配送员-查看评价
     *
     * @param pageDTO 分页信息
     * @return
     */
    @Override
    public Map<String, Object> senderCommentDisplay(PageDTO pageDTO) {
        // 查询用户未删除的订单
        QueryWrapper<UserOrderSender> userOrderSenderWrapper = new QueryWrapper<>();
        userOrderSenderWrapper.eq("sender_id", pageDTO.getUserId());
        List<String> orderIdList = userOrderSenderDao.selectList(userOrderSenderWrapper).stream().map(UserOrderSender::getOrderId).collect(toList());
        // 封装数据
        List<CommentVO> commentVOList = new ArrayList<>();
        if (ObjectUtils.isNotNull(orderIdList)) {
            LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
            commentWrapper.eq(ObjectUtils.isNotNull(pageDTO.getEvaluation()), Comment::getEvaluation, pageDTO.getEvaluation())
                    .between(ObjectUtils.isNotNull(pageDTO.getStartTime()) &&
                            ObjectUtils.isNotNull(pageDTO.getEndTime()), Comment::getCreateTime, pageDTO.getStartTime(), pageDTO.getEndTime())
                    .in(Comment::getOrderId, orderIdList);
            // 分页
            Page<Comment> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
            Page<Comment> commentPage = commentDao.selectPage(page, commentWrapper);

            commentPage.getRecords().forEach(comment -> {
                CommentVO commentVO = CommentVOFactory.createSenderCommentVO(comment);
                commentVOList.add(commentVO);
            });
            return ImmutableMap.<String, Object>builder()
                    .put(PAGE_TOTAL, commentPage.getTotal())
                    .put(COMMENT_LIST, commentVOList)
                    .build();
        }
        return ImmutableMap.<String, Object>builder()
                .put(PAGE_TOTAL, NO_ONE)
                .put(COMMENT_LIST, commentVOList)
                .build();
    }

    /**
     * 管理员-查看评价
     *
     * @param pageDTO 分页信息
     * @return
     */
    @Override
    public Map<String, Object> adminCommentDisplay(PageDTO pageDTO) {
        // 查询所有评价
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(ObjectUtils.isNotNull(pageDTO.getOrderId()), Comment::getOrderId, pageDTO.getOrderId())
                .eq(ObjectUtils.isNotNull(pageDTO.getEvaluation()), Comment::getEvaluation, pageDTO.getEvaluation())
                .between(ObjectUtils.isNotNull(pageDTO.getStartTime()) && ObjectUtils.isNotNull(pageDTO.getEndTime())
                        , Comment::getCreateTime, pageDTO.getStartTime(), pageDTO.getEndTime());
        Page<Comment> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        Page<Comment> commentPage = commentDao.selectPage(page, commentWrapper);
        // 封装数据
        List<CommentVO> commentVOList = new ArrayList<>();
        commentPage.getRecords().forEach(comment -> {
            // 查询用户账号和配送员账号
            QueryWrapper<UserOrderSender> userOrderSenderWrapper = new QueryWrapper<>();
            userOrderSenderWrapper.eq("order_id", comment.getOrderId());
            UserOrderSender userOrderSender = userOrderSenderDao.selectOne(userOrderSenderWrapper);
            UserInfo userInfo = userInfoDao.selectById(userOrderSender.getUserId());
            Sender sender = senderDao.selectById(userOrderSender.getSenderId());
            CommentVO commentVO = CommentVOFactory.createAdminCommentVO(comment, userInfo, sender);
            commentVOList.add(commentVO);
        });
        return ImmutableMap.<String, Object>builder()
                .put(PAGE_TOTAL, commentPage.getTotal())
                .put(COMMENT_LIST, commentVOList)
                .build();

    }

    /**
     * 管理员-删除评价
     *
     * @param commentDTO 评价信息
     * @return
     */
    @Override
    public void adminDeleteComment(CommentDTO commentDTO) {
        // 删除评价
        commentDao.deleteById(commentDTO.getCommentId());
        // 添加日志
        Admin admin = adminDao.selectById(commentDTO.getUserId());
        String content = "管理员:" + admin.getUsername() + ",删除评价";
        commonService.recordLog(commentDTO.getOrderId(), content);
    }
}
