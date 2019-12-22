FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER zhaobohao@sohu.com

RUN mkdir -p /springclouddeve/desk

WORKDIR /springclouddeve/desk

EXPOSE 8105

ADD ./target/springcloud-desk.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
