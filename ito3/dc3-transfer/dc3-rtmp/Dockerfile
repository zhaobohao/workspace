

FROM pnoker/alpine-java:1.8.251 AS base
MAINTAINER pnoker pnokers.icloud.com

ENV JAVA_OPS -server -Xms128m -Xmx512m -XX:CompressedClassSpaceSize=128m -XX:MetaspaceSize=200m -XX:MaxMetaspaceSize=200m

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && mkdir -p /dc3-transfer/dc3-rtmp \
    && mkdir -p /dc3-transfer/dc3-rtmp/dc3/logs

FROM jrottenberg/ffmpeg:4.1-alpine AS build

FROM base AS release

WORKDIR /dc3-transfer/dc3-rtmp

ADD ./target/dc3-rtmp.jar ./
COPY --from=build /usr/local /usr/local

EXPOSE 8803
VOLUME /dc3-transfer/dc3-rtmp/dc3/logs

CMD java ${JAVA_OPS} -Djava.security.egd=file:/dev/./urandom -jar dc3-rtmp.jar

