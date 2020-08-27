

FROM pnoker/alpine-java:1.8.251
MAINTAINER pnoker <pnokers.icloud.com>

ENV JAVA_OPS -server -javaagent:/usr/local/java/lib/aspectjweaver.jar -Xms128m -Xmx512m -XX:CompressedClassSpaceSize=128m -XX:MetaspaceSize=200m -XX:MaxMetaspaceSize=200m

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && mkdir -p /dc3-center/dc3-manager \
    && mkdir -p /dc3-center/dc3-manager/dc3/logs

WORKDIR /dc3-center/dc3-manager

ADD ./target/dc3-manager.jar ./

EXPOSE 8400
VOLUME /dc3-center/dc3-manager/dc3/logs

CMD java ${JAVA_OPS} -Djava.security.egd=file:/dev/./urandom -jar dc3-manager.jar
