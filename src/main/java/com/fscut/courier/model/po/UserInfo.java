package com.fscut.courier.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 普通用户实体类
 * id
 * username;//用户名字
 * password;//密码
 * phone;//手机号
 * sex;//性别(0是男 1是女)
 * status;//状态(1启用 0禁用)
 * photo;//头像
 * faceData;//人脸特征值
 * address地址
 * note备注
 */
@Data
@TableName("userinfo")
public class UserInfo {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String photo;//头像
    /**
     * 人脸状态（1启用，0禁用）
     */
    private Integer status;
    /**
     * 人脸特征值
     */
    private byte[] faceData;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 备注
     */
    private String note;
    /**
     * 短信验证码
     */
    @TableField(exist = false)
    private String smscode;
    /**
     * base64的图片
     */
    @TableField(exist = false)
    private String img;
    /**
     * 登陆者类型
     */
    @TableField(exist = false)
    private String loginType;


}
