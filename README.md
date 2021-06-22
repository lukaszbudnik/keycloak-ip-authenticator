# keycloak-ip-authenticator

This is a simple Keycloak Java Authenticator that checks if the user is coming from a trusted network or not. If the user is coming from a trusted network MFA step is skipped. If the user is coming from a non-trusted network MFA step is forced.

The authenticator has to be used together with `Conditional OTP Form` component.

See the following Youtube video which explains how to deploy and configure it in Keycloak: https://youtu.be/u36QK9oyrtM.

## build

Make sure that Keycloak SPI dependencies and your Keycloak server versions match. Keycloak SPI dependencies version is configured in `pom.xml` in the `keycloak.version` property.  

To build the project execute the following command:

```bash
mvn package
```

## deploy

And then, assuming `$KEYCLOAK_HOME` is pointing to you Keycloak installation, just copy it into deployments directory:
 
```bash
cp target/keycloak-ip-authenticator.jar $KEYCLOAK_HOME/standalone/deployments/
```
