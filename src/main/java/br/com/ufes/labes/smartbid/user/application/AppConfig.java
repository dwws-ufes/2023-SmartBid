package br.com.ufes.labes.smartbid.user.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@CustomFormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue(
        loginPage = "/login.xhtml", useForwardToLogin = false, errorPage = ""))
@DatabaseIdentityStoreDefinition(dataSourceLookup = "java:jboss/datasources/smartbid",
        callerQuery = "select senha from Pessoa p where identificacao = ?",
        groupsQuery = "select role from Pessoa p where identificacao = ?")
@FacesConfig
@ApplicationScoped
public class AppConfig {

}
