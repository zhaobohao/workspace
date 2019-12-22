FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER zhaobohao@sohu.com

RUN mkdir -p /springclouddeve/gateway

WORKDIR /springclouddeve/gateway

EXPOSE 80

ADD ./target/springcloud-gateway.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
