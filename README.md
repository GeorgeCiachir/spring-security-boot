# A small app that exemplifies the use of several spring security features and configurations
## It can run as a Spring Boot application, with the default embedded Tomcat container or a Jetty container, or it can be deployed in a Tomcat or Jetty container, by selecting the maven profiles

## Available maven profiles:
- STANDALONE_TOMCAT
- STANDALONE_JETTY
- CONTAINER_TOMCAT
- CONTAINER_JETTY

## Available configurations based on Spring profiles:
- DEFAULT_AUTH_PROVIDER - use the default authentication manager in the DefaultAuthenticationProviderSecurityConfig class
- CUSTOM_AUTH_PROVIDER - use the custom authentication manager in the CustomAuthenticationProviderSecurityConfig class
- CUSTOM_SECURITY_FILTER - use a custom securityFilter and the application's custom security controller for login, with a custom page
- IN_MEMORY_AUTH_WITH_SPRING_SEC_CONTROLLER_LOGIN - use an in-memory authentication and the default spring security controller for login, with a custom page