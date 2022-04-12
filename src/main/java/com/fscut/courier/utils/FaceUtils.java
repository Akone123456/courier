package com.fscut.courier.utils;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

@Data
@Component
public class FaceUtils {

    @Value("${face.appId}")
    public  String appId1;
    @Value("${face.sdkKey}")
    public  String sdkKey1;
    @Value("${face.dllPath}")
    public  String dllPath1;

    public static String appId;
    public static String sdkKey;
    public static String dllPath;

    @PostConstruct
    public void init(){
        appId = this.appId1;
        sdkKey = this.sdkKey1;
        dllPath = this.dllPath1;
    }
    /**
     * 初始化
     */
    public static FaceEngine getFaceEngine() {
        FaceEngine faceEngine = new FaceEngine(dllPath);
        //激活引擎
        int errorCode = faceEngine.activeOnline(appId, sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }
        ActiveFileInfo activeFileInfo=new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);


        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }

        return faceEngine;
    }



    /**
     * 获取一张照片的特征值
     * @param bytes
     * @return
     */
    public static FaceData getFaceData(byte[] bytes){

        FaceData faceData = new FaceData();
        //人脸检测
        ImageInfo imageInfo = getRGBData(bytes);
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        FaceEngine faceEngine = FaceUtils.getFaceEngine();
        int errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);

        System.out.println("人脸检测状态码："+errorCode);
        faceData.setValidateFace(errorCode);

        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        int code=faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        System.out.println("特征值大小：" + faceFeature.getFeatureData().length);
        System.out.println("特征值状态码：" + code);
        faceData.setValidatePoint(code);

        faceData.setFaceData(faceFeature.getFeatureData());

        //引擎卸载
        faceEngine.unInit();
        return faceData;
    }

    /**
     *  特征值比对（返回相似度）
     * @param sources
     * @param target
     * @return
     */
    public static CompareFace compare(byte[] sources,byte[] target){
        CompareFace compareFace = new CompareFace();
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(target);
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(sources);
        FaceSimilar faceSimilar = new FaceSimilar();
        FaceEngine faceEngine = FaceUtils.getFaceEngine();
        int scoreCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
        System.out.println("相似度状态码："+scoreCode);
        compareFace.setScoreCode(scoreCode);
        compareFace.setScore(faceSimilar.getScore());
        //引擎卸载
        faceEngine.unInit();
        return compareFace;
    }




}
