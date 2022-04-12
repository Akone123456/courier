package com.fscut.courier.utils;

import lombok.Data;

@Data
public class FaceData {

    private int validateFace;//检测人脸的状态码（判断是否检测到人脸）
    private int validatePoint;//获取特征值状态码（判断是否采集成功）
    private  byte[] faceData;//特征值
}
