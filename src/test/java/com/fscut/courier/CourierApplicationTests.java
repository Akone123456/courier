package com.fscut.courier;

import com.fscut.courier.config.TxSmsTemplate;
import com.fscut.courier.dao.*;
import com.fscut.courier.model.po.*;
import com.fscut.courier.service.AddressService;
import com.fscut.courier.utils.OrderStatusEnum;
import com.fscut.courier.utils.PayStatusEnum;
import com.sun.DateUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class CourierApplicationTests {
    @Autowired
    private TxSmsTemplate txSmsTemplate;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private AddressUserInfoDao addressUserInfoDao;
    @Autowired
    private SenderDao senderDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserOrderSenderDao userOrderSenderDao;
    @Autowired
    private CommentDao commentDao;


    public static char [] ch={'a','b','c','d','e','f','g','h','j','k','l','m','n','o','p','q','r','s','t',
    'u','v','w','x','y','z'};
    public static String [] name = {"王静", "王瑶", "李茹悦", "王紫灵", "岑佳", "戴志萍", "郁方正", "姜佳宏", "彭嘉融", "颜沁雨",
    "王君", "皇丙蕊", "华思烨", "谢卓希", "蔡银", "胡雨佳", "许佳虹", "黄燕", "胡智雅", "柏嘉雯", "李婷", "刘晴", "万文华", "江文",
    "刘新伟", "政佳伟", "匡奕", "田浩澄", "陈宇超", "王博", "吴学海", "陈威宇", "施程逸", "杨承浚", "陆国伟", "张忠阳", "嵇道磊",
    "王尧", "陈康", "丁益飞", "周开熊", "张锋", "倪佳雷", "吕昊", "姜鹏", "朱方成", "刘凯敏", "周斌", "郑江南"};

    public static String [] phone = {"17312846901", "15189690397", "19851637580", "13951464097", "15962882106", "13228879734", "13506253473",
    "13773878833", "13621587105", "17348357576", "18251936360", "17354949769", "19851661225", "19851661286", "17751730559", "19851637626",
    "13776218759", "15862764040", "19851661168", "13182376100", "15506105381", "19851662352", "15862808010", "15366756861", "19951753185",
    "13365295907", "13083537210", "17354948994", "13915627559", "13222177440", "18761599805", "13813707997", "13813766913", "13375102071", "17354940839", "13101822613",
    "19952339367", "14705221198", "17551038827", "15862764908", "13151218012", "13083546806", "15250622423", "13348132280",
    "13952713055", "17625957305", "13205101952", "18136122367", "17768651125"};

    public static String [] addressinfo = {"中心2A#405", "中心2A#405", "中心2A#406", "中心2A#406", "中心2A#406",
    "中心2A#406", "中心2A#406", "中心2A#406", "中心2A#409", "中心2A#409", "中心2A#409", "中心2A#409", "中心2A#409", "中心2A#409",
    "中心2A#425", "中心2A#425", "中心2A#425", "中心2A#425", "中心2A#425", "中心2A#501", "中心2A#501", "中心2A#501", "中心7B#112",
    "中心7B#112", "中心7B#112", "中心7B#113", "中心7B#113", "中心7B#113", "中心7B#113", "中心7B#113", "中心7B#113", "中心7B#116", "中心7B#114", "中心7B#114",
    "中心7B#114", "中心7B#114", "中心7B#114", "中心7B#115", "中心7B#115", "中心7B#115", "中心7B#115", "中心7B#115", "中心7B#115",
    "中心7B#116", "中心7B#116", "中心7B#116", "中心7B#116", "中心7B#116", "中心7B#116"};
    /**
     * 腾讯云发送短信测试
     */
    @Test
    public void TxSmsTest(){
        // 参数1: 手机号(正文模板中的参数{1})
        // 参数2: 验证码(正文模板中的参数{2})
        String Msg = txSmsTemplate.sendMesModel("15336574540", "1285");
        // Msg不为null 发送成功
        // Msg为null  发送失败
        System.out.println(Msg);
    }

    @Test
    void contextLoads() {
    }
    @Test
    public void shiliuweiTest(){

        for(int p = 0;p<10;p++){
            String code = "";
            for(int i = 0;i<12;i++){
                int k =  (int) (Math.random()*9+1);
                code += String.valueOf(k);
            }
            System.out.println(code);
        }

    }
    @Test
    //@Transactional(rollbackFor = Exception.class)
    public void InsertUser() {
        try {
            for(int i=0;i<400;i++){
                String code = "";
                for(int p=0;p<11;p++){
                    int k = (int)(Math.random()*9+1);
                    code+=String.valueOf(k);
                }
                UserInfo userInfo = new UserInfo();
                userInfo.setPhone(code);
                userInfo.setPassword("123456");
                String username = "";
                for(int k= 0 ;k<6;k++){
                    int p = (int)(Math.random()*25);
                    username+=ch[p];
                }

                userInfo.setUsername(username);
                userInfo.setCreateTime(DateUtils.getNowDateString());
                userInfoDao.insert(userInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 添加我的收货地址
     */
    @Test
    //@Transactional(rollbackFor = Exception.class)
    public void InsertAddress() {
        try {
            List<UserInfo> userInfos = userInfoDao.selectList(null);
            for(int i=2;i<userInfos.size();i++){
                UserInfo userInfo = userInfos.get(i);
                for(int k=0;k<5;k++){
                    Address address = new Address();
                    address.setConsignee(userInfo.getUsername());
                    address.setPhone(userInfo.getPhone());
                    address.setCity("江苏省徐州市云龙区");
                    address.setAddress("大龙湖街道徐州工程学院7B"+String.valueOf(112+i));
                    if(k%3==0){
                        address.setLabel("学校");
                    }else if(k%3==1) {
                        address.setLabel("家");
                    }else if (k%3==2){
                        address.setLabel("公司");
                    }
                    address.setIsDefault(0);
                    addressDao.insert(address);
                    AddressUserInfo addressUserInfo = new AddressUserInfo();
                    addressUserInfo.setUserId(userInfo.getId());
                    addressUserInfo.setAddressId(address.getId());
                    addressUserInfoDao.insert(addressUserInfo);

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 添加配送员
     */
    @Test
    //@Transactional(rollbackFor = Exception.class)
    public void InsertSender() {
        try {
            List<UserInfo> userInfos = userInfoDao.selectList(null);
            for(int i=2;i<userInfos.size();i++){
                UserInfo userInfo = userInfos.get(i);
                for(int k=0;k<5;k++){
                    Address address = new Address();
                    address.setConsignee(userInfo.getUsername());
                    address.setPhone(userInfo.getPhone());
                    address.setCity("江苏省徐州市云龙区");
                    address.setAddress("大龙湖街道徐州工程学院7B"+String.valueOf(112+i));
                    if(k%3==0){
                        address.setLabel("学校");
                    }else if(k%3==1) {
                        address.setLabel("家");
                    }else if (k%3==2){
                        address.setLabel("公司");
                    }
                    address.setIsDefault(0);
                    addressDao.insert(address);
                    AddressUserInfo addressUserInfo = new AddressUserInfo();
                    addressUserInfo.setUserId(userInfo.getId());
                    addressUserInfo.setAddressId(address.getId());
                    addressUserInfoDao.insert(addressUserInfo);

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 添加订单
     */
    @Test
    //@Transactional(rollbackFor = Exception.class)
    public void InsertOrder() {
        try {
            List<UserInfo> userInfos = userInfoDao.selectList(null);
            List<Sender> senders = senderDao.selectList(null);

            for(int i=30;i<80;i++){

                UserInfo userInfo = userInfos.get(i);
                Sender sender = senders.get(i);
                Order order = new Order();
                order.setOrderId(String.valueOf(System.currentTimeMillis()));
                order.setTakeUserName(userInfo.getUsername());
                order.setPhone(userInfo.getPhone());
                String code = "";
                for(int p = 0;p<12;p++){
                    int k =  (int) (Math.random()*9+1);
                    code += String.valueOf(k);
                }
                order.setCourierNumber(code);
                List<Address> addressList = addressDao.selectAllByUserId(userInfo.getId());
                String addressinfo = addressList.get(0).getCity() + addressList.get(0).getAddress();
                order.setCourierAddress(addressinfo);
                order.setBounty(BigDecimal.valueOf(Math.random()*100));
                order.setPayStatus(PayStatusEnum.HAVE_PAY.getStatus());
                order.setOrderStatus(OrderStatusEnum.NOT_ORDER.getStatus());
                orderDao.insert(order);

                UserOrderSender userOrderSender = new UserOrderSender();
                userOrderSender.setOrderId(order.getOrderId());
                userOrderSender.setUserId(userInfo.getId());
                //userOrderSender.setSenderId(sender.getId());
                userOrderSenderDao.insert(userOrderSender);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Test
    //@Transactional(rollbackFor = Exception.class)
    public void InsertComment() {
        try {
            for(int i=0;i<100;i++){
                Comment comment = new Comment();
                comment.setOrderId(String.valueOf(System.currentTimeMillis()));
                comment.setEvaluation(1);
                comment.setCommentNote("配送源送货及时");
                commentDao.insert(comment);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testTrueStudent(){
        int len = name.length;
        for(int i=0;i<len;i++){
            UserInfo userInfo = new UserInfo();
            userInfo.setPhone(phone[i]);
            userInfo.setPassword("123456");
            userInfo.setUsername(name[i]);
            userInfoDao.insert(userInfo);
        }
    }

    @Test
    public void testTrueStudentAddress(){
        List<UserInfo> userInfos = userInfoDao.selectList(null);
        for(int i=402;i<451;i++){
            UserInfo userInfo = userInfos.get(i);
            Address address = new Address();
            address.setConsignee(userInfo.getUsername());
            address.setPhone(userInfo.getPhone());
            address.setCity("江苏省徐州市云龙区");
            address.setAddress("大龙湖街道徐州工程学院"+addressinfo[i-402]);
            address.setLabel("学校");
            address.setIsDefault(0);

            addressDao.insert(address);
            AddressUserInfo addressUserInfo = new AddressUserInfo();
            addressUserInfo.setUserId(userInfo.getId());
            addressUserInfo.setAddressId(address.getId());
            addressUserInfoDao.insert(addressUserInfo);

        }
    }


}
