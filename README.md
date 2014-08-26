autoProjectFrame
================
1.用法
   下载项目，直接执行ConditionJFrame中的main方法会弹出对话框，输入数据库基本信息，然后选择你要生成的表点击执行。
生成的内容会默认在D:\autoProject目录下，包括Pojo、Bo、Dao、mybatis配置文件。
但是，生成的内容肯定不是你想要的，因为这是按照我们的框架规则来生成的，如果想要生成你要的内容详见2.

2.生成自定义内容
   a.系统默认的是连接mysql数据库，若要自定义，在DBPassport类中修改driver 和 url内容；
   b.系统默认生成内容在D:\autoProject，若要自定义，在ProjectFrameProducer中修改projectDir；
   c.若要自定义生成Bo、Dao、mybatis配置文件（工具还可以生成action、spring、struts配置等），则需要修改
   fileProductor文件夹下的类，比如ProjectFrameProducer、SqlMapFileProductor、PojoFileProductor等。
   
   over
