# dubbo-springboot-project
一个dubbo的springboot项目，dubbo版本用的是最新的apache的版本，包含登录、推送、分布式定时任务等项目。

[dobbo-官网](http://dubbo.apache.org/zh-cn/)
[dubbo-github](https://github.com/apache/dubbo)
[dubbo-admin](https://github.com/apache/dubbo-admin)
[springboot-官网](https://spring.io/projects/spring-boot/)

### 目录结构说明

- common-parent：从名字可以看出，它是整个项目的父pom文件，里面主要包含了springboot框架的版本，和一些常用工具包，方便以后升级版本（注：我将dubbo remote包也放进去了，如果有强迫症的同学可以重新命名一个包，控制每个分布式服务的包的版本）

- common-base：基本包，也是整个项目的基本包。里面主要增加了一个dubbo框架的处理。
	* `自定义脱敏（Desensitization）：这个自定义脱敏是基于fastjson，用法如下 注：我将springboot默认的jsckson序列化方式修改成了fastjson`
	
		```java
		@Desensitization(endIndex = 3)
		private String loginName;

		private String nickName;

		@Desensitization(DesensitizationType.REAL_NAME)
		private String userName;

		@Desensitization(DesensitizationType.MOBILE)
		private String mobile;

		@Desensitization(DesensitizationType.EMAIL)
		private String email;
		```
		
	* `dubbo服务rpc异常类（RemoteServiceException）：每个服务可以自己去继承该接口，方便后续日志或者异常统计`
	* `dubbo服务调用异常监听过滤器（RemoteServiceExceptionFilter）：apache实现方式有所修改，需要继承其抽象类 ListenableFilter，有兴趣同学可以去官网查看 注：也需要在resources文加下添加一个文件：META-INF/dubbo/org.apache.dubbo.rpc.Filter`
	* `消息返回体：Result -> 非分页查询所有返回体；PageResult：分页查询返回体`
	* `util包下放一些常用的工具类，大家可以自定义添加，先主要有实体类拷贝、时间、以及spring容器...`
	
- common-dao：服务中dao层需要引用，定义了通用mapper的顶层接口（mapper）、service层的顶层接口以及抽象类，所有大家对于单表操作不再需要维护一个*Mapper.xml文件了，当然了我在这里也提倡大家尽量单表操作，将多表关系在业务层来实现

- common-controller：是api服务需要引用的。
	* `增加一个swagger的依赖，大家可以通过 http://ip:port/doc.html`的url去访问我们api层面的接口文档，比swagger-ui.html界面更加美观（我是这么觉得的），需要大家自己在自己的api层继承该类：SwaggerBaseConfigurer
	* `统一异常处理（ControllerExceptionHandleAdvice）：我只做了对controller层的统一处理，有需要可以自己添加修改`
	* `springmvc的配置（WebMvcConfigurer），有兴趣的小伙伴可以自己看一看，主要是对springboot默认序列化的修改替换以及swagger的静态资源的访问权限`
	
- common-security：顾名思义，springboot的安全配置，这里只讲几点，有兴趣的同学可以自己去研究下security，后续还有增加一个有访问权限的security，适用于管理后台的api
	* `注解：Anonymous：加在方法或者类上，代表这个方法或者该类下所有的mapping方法可以免鉴权访问，否则所有的api必须登录后写到token来访问`
	* `类（JwtUserDetailsServiceImpl）：远程调用member服务进行token有效性、正式性的校验判断实现类，如果需要修改，可以自行修改`
	
- common-export：自定义注解导出，只需要在实体类的field加上注解（Export即可），并且暂时只有csv的导出，如果需要excel的导出，大家可以自行添加

- service-member：用户服务，可以自己扩展

- service-datasource：多数据源数据质量管理服务

- api-web：对外统一的api出口，当然了大家也可以在每个服务对外开放api，看情况而定