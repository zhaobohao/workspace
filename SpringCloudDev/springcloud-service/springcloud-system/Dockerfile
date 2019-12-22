FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER zhaobohao@sohu.com

RUN mkdir -p /springclouddeve/system

WORKDIR /springclouddeve/system

EXPOSE 8106

ADD ./target/springcloud-system.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

CMD ["--spring.profiles.active=test"]
