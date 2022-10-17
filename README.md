# keycloak-ip-authenticator

This is a simple Keycloak Java Authenticator that checks if the user is coming from a trusted network or not. If the user is coming from a trusted network MFA step is skipped. If the user is coming from a non-trusted network MFA step is forced.

The authenticator has to be used together with `Conditional OTP Form` component.

See the following Youtube video which explains how to deploy and configure it in Keycloak: https://youtu.be/u36QK9oyrtM.

## build

The Keycloak SPI is very stable but always make sure that Keycloak SPI dependencies and your Keycloak server versions match. Keycloak SPI dependencies version is configured in `pom.xml` in the `keycloak.version` property.

To build the project execute the following command:

```bash
mvn package
```

## deploy

Assuming `$KEYCLOAK_HOME` is pointing to you Keycloak installation.

If you use legacy Keycloak running on WildFly copy it into deployments directory:
 
```bash
cp target/keycloak-ip-authenticator.jar $KEYCLOAK_HOME/standalone/deployments/
```

If you use latest Keycloak running on Quarkus copy it into providers directory:

```bash
cp target/keycloak-ip-authenticator.jar $KEYCLOAK_HOME/providers/
```
