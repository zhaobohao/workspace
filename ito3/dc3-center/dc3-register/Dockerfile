FROM pnoker/alpine-java:1.8.251
MAINTAINER pnoker <pnokers.icloud.com>

ENV JAVA_OPS -server -Xms128m -Xmx512m -XX:CompressedClassSpaceSize=128m -XX:MetaspaceSize=200m -XX:MaxMetaspaceSize=200m

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && mkdir -p /dc3-center/dc3-register

WORKDIR /dc3-center/dc3-register

ADD ./target/dc3-register.jar ./

EXPOSE 8100

CMD java ${JAVA_OPS} -Djava.security.egd=file:/dev/./urandom -jar dc3-register.jar
