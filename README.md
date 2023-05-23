# FB-init
前后端分离项目模版

前端Vue3+后端SpringBoot3

包含基本的登录、注册、密码重置等等功能，可以二次开发编写具体场景下的应用程序。
- 登录功能（支持用户名、邮箱登录）
- 注册功能（通过邮箱注册）
- 重置密码（通过邮箱重置密码）

截图：
![登录](doc/登录页面.png)
![注册](doc/注册页面.png)
![重置](doc/重置密码页面.png)


ps：
启动前项目前：
1. `application.yml` 要更改数据库和邮箱配置
2. `SecurityConfiguration`文件中`jdbcTokenRepository.setCreateTableOnStartup(false);`需要改为true，以便在数据库中创建登录信息表，用以记录当前用户的登录信息
3. 发送邮件接口实现类中，需要打开邮件发送的代码，方便开发，发送邮件功能注释掉了，直接在redis中读取验证码。` mailSender.send(message);`记得启用这条代码，当然前提是你正确配置了你的邮箱地址。
