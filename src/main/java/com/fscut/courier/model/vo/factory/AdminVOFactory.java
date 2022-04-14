package com.fscut.courier.model.vo.factory;

import com.fscut.courier.model.po.Admin;
import com.fscut.courier.model.vo.AdminVO;

/**
 * @author lxw
 */
public class AdminVOFactory {
    private AdminVOFactory() {

    }
    public static AdminVO createAdminVO(Admin admin){
        return AdminVO.builder()
                .userId(admin.getId())
                .userName(admin.getUsername())
                .build();
    }
}
