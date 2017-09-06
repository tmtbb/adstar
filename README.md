# adstar


### 项目用到的技术

- 1.个推服务框架
- 2.LeakCanary内存泄漏排查框架
- 3.BufferKnife注解绑定框架
- 4.GreenDao数据库框架


#### 关于项目网络框架

- 1.项目检查更新，下载安装包使用的是http协议
- 2.项目和服务器的通讯使用TCP/IP协议，封装socket进行数据交互


#### 简单描述SplashActivity的逻辑：

1>初始化BugOut框架

2>handler延时2秒，然后开启新的Activity

3>Thread开启线程

步骤1：使用GetIPAddressUtils的getIpAddress()获取IP地址相关信息，内部使用一个HttpUtils对象，而HttpUtils的内部是使用Android原生的HttpURLConnection来进行数据请求的；
请求 http://ip.chinaz.com/getip.aspx； 获取到Json数据，封装为一个MyIpBean对象：

 ```
 final MyAddressBean ipAddress = GetIPAddressUtils.getIpAddress();

{ip:'125.122.238.73',address:'浙江省杭州市 电信'}
```

步骤2：继续请求： http://ip.taobao.com/service/getIpInfo.php?ip=125.122.238.73； 后面的IP地址就是我们初次获取到的，继续获取到Json数据，封装成一个yAddressBean的对象。

```json
  {"country":"中国","country_id":"CN","area":"华东","area_id":"300000","region":"浙江省","region_id":"330000","city":"杭州市","city_id":"330100","county":"","county_id":"-1","isp":"电信","isp_id":"100017","ip":"125.121.228.115"}
```

步骤3：把获取到的MyAddressBean对象的重要信息 (Area_id_区域ID，area_区域名称，isp_id_运行商IP，isp_运行商名称) 赋值给一个全局对象AppConfig

```
    AppConfig.AREA_ID = Long.valueOf(ipAddress.getData().getArea_id());
    AppConfig.AREA = ipAddress.getData().getArea();
    AppConfig.ISP_ID = Long.valueOf(ipAddress.getData().getIsp_id());
    AppConfig.ISP = ipAddress.getData().getIsp();
```

步骤4：使用NetworkAPIFactoryImpl

SocketAPINettyBootstrap对象使用startConnect方法，内部bootstrap.connect(new InetSocketAddress(networkAPIConfig.getSocketServerIp(), networkAPIConfig.getSocketServerPort())).sync()和数据服务器进行初始化连接

项目的底层使用的是SocketChannel和服务器进行数据交互的


1、SplashActivity调用NetWorkAPIFactoryImp对象，
2、NetWorkAPIFactoryImp对象继续调用SocketAPIFactoryImpl對象
3、SocketAPIFactoryImpl對象继续调用SocketUserAPI
4、SocketUserAPI继续调用自身的getQiNiuPicDress()方法获取图片服务器IP地址