#### 项目介绍
工商图片识别（Businessinfd）基于Opencv ,tesseract4.0。JavaCpp 实现对与tmall的工商图片进行信息识别，实现数据的结构化处理。

 :tw-1f1e8-1f1f3:  :fa-envelope-o: daiguoweistyel@foxmail.com 

#### 软件架构
软件架构说明
描述 | 框架
- | :-: | -: 
视觉处理 | Opencv 3.4.1| 
OCR处理 | tesseract4.0 | 
训练手段 | LSTM | 
多媒体框架 | JDK 1.8 自带 |

### 项目结构

```

─data 
├─images   存放要处理的资源
├─imgs 	   软件结构目录
├─opencv   动态链接库目录
│  └─x64
├─out 		打包目录
│  └─artifacts
│      └─businessinformationdiscern_jar
├─src         源码目录
│  ├─main
│  │  ├─java
│  │  │  ├─domain    实体动作类
│  │  │  ├─main		 程序入口
│  │  │  ├─META-INF
│  │  │  ├─services   服务类
│  │  │  │── Form 	  UI类
│  │  │  └─utils 
│  │  └─resources     资源目录
│  └─test
│      └─java 		  条件测试相关类
├─target			  下面是编译生成目录
│  ├─classes
│  │  ├─domain
│  │  ├─main
│  │  ├─META-INF
│  │  └─utils
│  ├─generated-sources
│  │  └─annotations
│  ├─generated-test-sources
│  │  └─test-annotations
│  ├─maven-status
│  │  └─maven-compiler-plugin
│  │      ├─compile
│  │      │  └─default-compile
│  │      └─testCompile
│  │          └─default-testCompile
│  ├─surefire-reports
│  ├─test-classes
│  ├─win32-x86
│  └─win32-x86-64
└─tessdata
    └─configs

```
#### 1.2 展示

![输入图片说明](https://images.gitee.com/uploads/images/2018/0808/160348_ab9d33ff_1084454.png "1.png")

![输入图片说明](https://images.gitee.com/uploads/images/2018/0808/160401_c59ace86_1084454.png "2.png")

#### 安装教程

1. 导入Idea，并且确保你的环境安装Maven3.5（mvn -v）
2. Businessinfd/src/main/java/main 下ALLConfig 基本信息配置 （需理解Jna,javacpp）
3. Businessinfd/src/main/java/main 下App 即可运行

#### 使用说明

1. git本项目后下 确保天猫工商信息执照目录下包含50张左右的测试数据
2. 运行时间取决于你的机器性能，
3. 运行结束后会在项目下生成天猫工商信息.xls文件

#### 参与贡献

本人才学疏浅，代码难免问题较多，望各路大佬指正批评。

1. Fork 本项目
2. 新建 devver2 分支
3. 提交代码
4. 新建 Pull Request

#### 非常感谢
1. xxl-excel

#### 开源协议

 **MIT** 

#### 请我喝一杯茶
![PswvEq.png](https://s1.ax1x.com/2018/08/08/PswvEq.png)