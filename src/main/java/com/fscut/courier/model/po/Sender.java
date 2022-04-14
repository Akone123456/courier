package com.fscut.courier.model.po;

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
     * 真实姓名
     */
    private String realname;
    /**
     * 密码
     */
    private String password;
    /**
     * 账号状态（1启用 0禁用)
     */
    private Integer status;//状态(1启用 0禁用)
    /**
     * 性别(0男,1女)
     */
    private Integer sex;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 备注
     */
    private String note;

    /**
     * 登陆者类型（'sender'）
     */
    @TableField(exist = false)
    private String loginType;


}
