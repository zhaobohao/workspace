FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER zhaobohao@sohu.com

RUN mkdir -p /springclouddeve/user

WORKDIR /springclouddeve/user

EXPOSE 8102

ADD ./target/springcloud-user.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
