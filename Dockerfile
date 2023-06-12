# Use a custom WildFly for Jakarta EE 9 image from LabES.
FROM labesufes/wildfly-jakarta-ee

# Copy the Web application to the server.
RUN echo "=> Deploying the application..."
ADD target/SmartBid-1.0-SNAPSHOT.war /opt/wildfly/standalone/deployments/

