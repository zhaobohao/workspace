

FROM pnoker/alpine-java:1.8.251
MAINTAINER pnoker <pnokers.icloud.com>

ENV JAVA_OPS -server -Xms128m -Xmx512m -XX:CompressedClassSpaceSize=128m -XX:MetaspaceSize=200m -XX:MaxMetaspaceSize=200m

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && mkdir -p /dc3-driver/dc3-driver-plcs7 \
    && mkdir -p /dc3-driver/dc3-driver-plcs7/dc3/logs

WORKDIR /dc3-driver/dc3-driver-plcs7

ADD ./target/dc3-driver-plcs7.jar ./

EXPOSE 8601
VOLUME /dc3-driver/dc3-driver-plcs7/dc3/logs

CMD java ${JAVA_OPS} -Djava.security.egd=file:/dev/./urandom -jar dc3-driver-plcs7.jar
