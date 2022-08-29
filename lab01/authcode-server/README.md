基于授权码模式+Spring Security OAuth2的最简授权服务器
======

# 操作方式

### 1. 获取授权码（Client -> AuthorizationServer, get code）

浏览器请求：

http://localhost:8080/oauth/authorize?client_id=clientapp&redirect_uri=http://localhost:9001/callback&response_type=code&scope=read_userinfo

操作：
- 输入 application.properties 中配置的用户名密码：bobo/xyz
- 选择 "Approve" 然后点击 "Authorize" 同意授权

注意：
- **state 参数暂忽略**
- client_id, redirect_uri, scope, response_type 必须与在 AuthorizationServer 中注册的一致（貌似对 redirect_uri 有做一定的校验但不准确，后面再加内容可以成功）
  - response_type 参数对应 OAuth 2.0 模型的一种
    - 授权码 code
    - 简化 token
    - 密码 password
    - 客户端 client_credentials

响应案例：

http://localhost:9001/callback?code=8uYpdo

### 2. 获取访问令牌（Client -> Authorization Server, get access_token）
> postman: 获取访问令牌_authorization_code

curl -X POST --user clientapp:112233 http://localhost:8080/oauth/token -H
"content-type: application/x-www-form-urlencoded" -d
"code=8uYpdo&grant_type=authorization_code&redirect_uri=http%3A%2F%2Flocalh
ost%3A9001%2Fcallback&scope=read_userinfo"

案例响应：

```json
{
    "access_token": "36cded80-b6f5-43b7-bdfc-594788a24530",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "read_userinfo"
}
```


### 3. 调用API（Client -> ResourcesServer, get resources）
> postman: 调用 API /api/userinfo

curl -X GET http://localhost:8080/api/userinfo -H "authorization: Bearer 36cded80-b6f5-43b7-bdfc-594788a24530"

案例响应：

```json
{
    "name": "bobo",
    "email": "bobo@spring2go.com"
}
```
