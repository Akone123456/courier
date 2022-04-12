package com.fscut.courier.bean;

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
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String phone;//手机号
    private String username;
    private String password;
    private String photo;//头像
    private Integer status;//状态(1启用 0禁用)
    private byte[] faceData;//人脸特征值
    private String createTime;
    private String note;

    @TableField(exist = false)
    private  String smscode;
    @TableField(exist = false)
    private String img;//base64位的图片
    @TableField(exist = false)
    private String loginType;//登陆者类型（'userinfo'）



}
