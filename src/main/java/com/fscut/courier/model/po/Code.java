package com.fscut.courier.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author lxw
 */
@Data
@TableName("code")
public class Code {
    private Integer id;
    private String smsCode;
}
