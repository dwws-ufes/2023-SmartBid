FROM jboss/wildfly:latest

COPY target/SmartBid-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
