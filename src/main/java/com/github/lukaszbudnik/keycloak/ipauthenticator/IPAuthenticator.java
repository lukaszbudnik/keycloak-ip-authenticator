package com.github.lukaszbudnik.keycloak.ipauthenticator;

import java.util.Collections;
import java.util.Map;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.*;
import org.keycloak.models.credential.OTPCredentialModel;

public class IPAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(IPAuthenticator.class);
    private static final String IP_BASED_OTP_CONDITIONAL_USER_ATTRIBUTE = "ip_based_otp_conditional";

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        KeycloakSession session = context.getSession();
        RealmModel realm = context.getRealm();
        UserModel user = context.getUser();

        String remoteIPAddress = context.getConnection().getRemoteAddr();
        String allowedIPAddress = getAllowedIPAddress(context);

        if (!allowedIPAddress.equals(remoteIPAddress)) {
            logger.infof("IPs do not match. Realm %s expected %s but user %s logged from %s", realm.getName(), allowedIPAddress, user.getUsername(), remoteIPAddress);
            UserCredentialManager credentialManager = session.userCredentialManager();

            if (!credentialManager.isConfiguredFor(realm, user, OTPCredentialModel.TYPE)) {
                user.addRequiredAction(UserModel.RequiredAction.CONFIGURE_TOTP);
            }

            user.setAttribute(IP_BASED_OTP_CONDITIONAL_USER_ATTRIBUTE, Collections.singletonList("force"));
        } else {
            user.setAttribute(IP_BASED_OTP_CONDITIONAL_USER_ATTRIBUTE, Collections.singletonList("skip"));
        }

        context.success();
    }

    private String getAllowedIPAddress(AuthenticationFlowContext context) {
        AuthenticatorConfigModel configModel = context.getAuthenticatorConfig();
        Map<String, String> config = configModel.getConfig();
        return config.get(IPAuthenticatorFactory.ALLOWED_IP_ADDRESS_CONFIG);
    }

    @Override
    public void action(AuthenticationFlowContext context) {
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

    @Override
    public void close() {
    }

}
