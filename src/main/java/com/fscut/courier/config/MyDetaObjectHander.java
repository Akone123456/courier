package com.fscut.courier.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * @author lxw
 */
@Component
public class MyDetaObjectHander implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {

        Object createTime = getFieldValByName("createTime", metaObject);
        if(ObjectUtils.isEmpty(createTime)) {
            this.setFieldValByName("createTime",new Date(),metaObject);
        }

        Object updateTime = getFieldValByName("updateTime", metaObject);
        if(ObjectUtils.isEmpty(updateTime)) {
            this.setFieldValByName("updateTime",new Date(),metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("updateTime",new Date(),metaObject);

    }

}
