FROM registry.cn-hangzhou.aliyuncs.com/kancy/openjdk:8-jre-alpine
MAINTAINER kancy

ENV MODE default
ENV NACOS_SERVER_ADDR 127.0.0.1:8848
ENV NACOS_USERNAME nacos
ENV NACOS_PASSWORD nacos

ADD sentinel-dashboard-dynamic-1.7.2-beta.jar /app.jar
EXPOSE 8080
CMD java $JAVA_OPTS -Dsentinel.datasource.mode=$MODE \
 -Dsentinel.dashboard.datasource.nacos.serverAddr=$NACOS_SERVER_ADDR \
 -Dsentinel.dashboard.datasource.nacos.namespace=$NACOS_NAMESPACE \
 -Dsentinel.dashboard.datasource.nacos.username=$NACOS_USERNAME \
 -Dsentinel.dashboard.datasource.nacos.password=$NACOS_PASSWORD \
 -jar /app.jar