package com.fscut.courier.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("admin")
public class Admin {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;//账号
    private String password;//密码

    @TableField(exist = false)
    private String loginType;//登陆者类型（'admin'）
}
