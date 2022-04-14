package com.fscut.courier.model.vo.factory;

import com.fscut.courier.model.po.Sender;
import com.fscut.courier.model.po.UserInfo;
import com.fscut.courier.model.vo.SenderVO;
import com.fscut.courier.model.vo.UserInfoVO;

/**
 * @author lxw
 */
public class SenderVOFactory {
    private SenderVOFactory() {

    }

    public static SenderVO createSenderVo(Sender sender) {
        return SenderVO.builder()
                .userId(sender.getId())
                .realName(sender.getRealname())
                .userName(sender.getUsername())
                .phone(sender.getPhone())
                .note(sender.getNote())
                .build();
    }
}
