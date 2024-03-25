package com.benz.javaproject.config;


import com.benz.javaproject.enums.Role;
import com.benz.javaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.keycloak.adapters.authorization.spi.ConfigurationResolver;
import org.keycloak.adapters.authorization.spi.HttpRequest;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.keycloak.util.JsonSerialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.DefaultAddressStandardClaim;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final UserService userService;

    //@Autowired
    //JwtAuthConverter jwtAuthConverter;


    @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception{
        http.csrf(t -> t.disable());
        http.addFilterAfter(createPolicyEnforcerFilter(),
                BearerTokenAuthenticationFilter.class);
        http.sessionManagement(
                t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        return http.build();

    }

    private ServletPolicyEnforcerFilter createPolicyEnforcerFilter() {
        return new ServletPolicyEnforcerFilter(new ConfigurationResolver() {
            @Override
            public PolicyEnforcerConfig resolve(HttpRequest httpRequest) {
                try {
                    return JsonSerialization
                            .readValue(getClass().getResourceAsStream("/policy-enforcer.json"),
                                    PolicyEnforcerConfig.class);
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
        });
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)
//        throws Exception{
//        http.csrf(t -> t.disable());
//        http.authorizeHttpRequests(authorize -> {
//            authorize
//                    .requestMatchers(HttpMethod.GET,"/hissedarlar/all",
//                            "swagger-ui/**", "swagger-ui**", "/v3/api-docs/**", "/v3/api-docs**"
//
//                            ).permitAll()
//                    .anyRequest().authenticated();
//        });
//        http.oauth2ResourceServer(t -> {
//            t.jwt(Customizer.withDefaults());
//        });
//        http.sessionManagement(
//                t-> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );
//        return http.build();
//
//    }


    @Bean
    public DefaultMethodSecurityExpressionHandler msecurity(){
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
        return defaultMethodSecurityExpressionHandler;
    }
    @Bean
    public JwtAuthenticationConverter con(){
        JwtAuthenticationConverter c = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter cv = new JwtGrantedAuthoritiesConverter();
        cv.setAuthorityPrefix("");
        cv.setAuthoritiesClaimName("roles");
        c.setJwtGrantedAuthoritiesConverter(cv);
        return c;


    }

//    @Bean
    //çalışıyor!!
//    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
//        http
//                .oauth2Client()
//                .and()
//                .oauth2Login()
//                .tokenEndpoint()
//                .and()
//                .userInfoEndpoint();
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
//        http
//                .authorizeHttpRequests()
//                .requestMatchers("/unauthenticated", "/oauth2/**", "/login/**").permitAll()
//                .anyRequest()
//                .fullyAuthenticated()
//                .and()
//                .logout()
//                .logoutSuccessUrl("http://elanur.local:8081/realms/SpringBootKeycloak/protocol/openid-connect/logout?redirect_uri=http://localhost:8081/");
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
////                .authorizeHttpRequests()
////                .requestMatchers("/**")
////                .permitAll()
////                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/whoami")
//                .permitAll()
//                .and()
//                .authorizeHttpRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .oauth2ResourceServer().jwt();
//
//        return http.build();
////        http.csrf(AbstractHttpConfigurer::disable)
////                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**",
////                                "/v2/api-docs",
////                                "/v3/api-docs",
////                                "/v3/api-docs/**",
////                                "/swagger-resources",
////                                "/swagger-resources/**",
////                                "/configuration/ui",
////                                "/configuration/security",
////                                "/swagger-ui/**",
////                                "/webjars/**",
////                                "/swagger-ui.html")
////                        .permitAll()
////                        .requestMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name()) //controllerlar
////                        .requestMatchers("/api/v1/user").hasAnyAuthority(Role.USER.name())
////
////
////
////                        .anyRequest().authenticated())
////                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .authenticationProvider(authenticationProvider()).addFilterBefore(
////                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
////                );
////                return http.build();
//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userService.userDetailsService());
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
        throws Exception{
            return config.getAuthenticationManager();
    }

}
