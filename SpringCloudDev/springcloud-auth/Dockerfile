FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER zhaobohao@sohu.com

RUN mkdir -p /springclouddeve/auth

WORKDIR /springclouddeve/auth

EXPOSE 8010

ADD ./target/springcloud-auth.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
