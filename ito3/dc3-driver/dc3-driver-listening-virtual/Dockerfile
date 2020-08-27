

FROM pnoker/alpine-java:1.8.251
MAINTAINER pnoker <pnokers.icloud.com>

ENV JAVA_OPS -server -Xms128m -Xmx512m -XX:CompressedClassSpaceSize=128m -XX:MetaspaceSize=200m -XX:MaxMetaspaceSize=200m

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && mkdir -p /dc3-driver/dc3-driver-listening-virtual \
    && mkdir -p /dc3-driver/dc3-driver-listening-virtual/dc3/logs

WORKDIR /dc3-driver/dc3-driver-listening-virtual

ADD ./target/dc3-driver-listening-virtual.jar ./

EXPOSE 8700
VOLUME /dc3-driver/dc3-driver-listening-virtual/dc3/logs

CMD java ${JAVA_OPS} -Djava.security.egd=file:/dev/./urandom -jar dc3-driver-listening-virtual.jar
