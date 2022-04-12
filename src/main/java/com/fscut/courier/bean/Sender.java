package com.fscut.courier.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 配送员实体
 * id
 * username;//用户名字
 * password;//密码
 * phone;//手机号
 * sex;//性别(0是男 1是女)
 * note备注
 * status;//状态(1启用 0禁用)
 */
@Data
@TableName("sender")
public class Sender {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String phone;//手机号
    private String username;//账号
    private String realname;//姓名
    private String password;
    private Integer status;//状态(1启用 0禁用)
    private Integer sex;//性别（0男1女）
    private String createTime;
    private String note;


    @TableField(exist = false)
    private String loginType;//登陆者类型（'sender'）


}
