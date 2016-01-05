package demo;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

		  @Bean
		    public DataSource oauthDataSource() {
		        
		    	 BasicDataSource dataSource = new BasicDataSource();
		         dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		         dataSource.setUrl("jdbc:sqlserver://server:1433;databaseName=DBName");
		         dataSource.setUsername("user");
		         dataSource.setPassword("password");
		         return dataSource;	
		    	
		    }
		 
		    @Bean
		    public JdbcClientDetailsService clientDetailsService() {
		        return new JdbcClientDetailsService(oauthDataSource());
		    }

		    @Bean
		    public TokenStore tokenStore() {
		        return new JdbcTokenStore(oauthDataSource());
		    }

		    @Bean
		    public ApprovalStore approvalStore() {
		        return new JdbcApprovalStore(oauthDataSource());
		    }

		    @Bean
		    public AuthorizationCodeServices authorizationCodeServices() {
		        return new JdbcAuthorizationCodeServices(oauthDataSource());
		    }

		    @Override
		    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		        clients.withClientDetails(clientDetailsService());
		    }

		    @Override
		    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		    	oauthServer.allowFormAuthenticationForClients();
		    }

		    @Override
		    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		        endpoints
		                .approvalStore(approvalStore())
		                .authorizationCodeServices(authorizationCodeServices())
		                .tokenStore(tokenStore());
		    }
	}

}