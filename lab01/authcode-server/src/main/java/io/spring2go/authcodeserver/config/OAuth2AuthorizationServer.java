package io.spring2go.authcodeserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

//授权服务器配置（一般来说一个完整的系统只需要一个授权服务器，不考虑负载均衡）
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends
        AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        // 使用硬编码的模式注册 Client
        clients.inMemory()
            .withClient("clientapp")
            .secret("112233")
            .redirectUris("http://localhost:9001/callback") // 随便写的，暂时可以不用
            .authorizedGrantTypes("authorization_code") // 授权码模式
            .scopes("read_userinfo", "read_contacts");
    }

}
