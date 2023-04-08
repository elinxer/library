FROM liferay/jdk11:latest
VOLUME /tmp
ADD dbn-cloud-gateway-server/target/dbn-cloud-gateway-server.jar /data/app.jar
EXPOSE 8700
ENTRYPOINT ["java","-Duser.timezone=GMT+8","-Djava.security.egd=file:/dev/./urandom","-jar","/data/app.jar","--spring.profiles.active=dev"]
