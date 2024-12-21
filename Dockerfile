FROM openjdk:21-jdk-slim-buster

# Environment Variables
ENV JAVA_OPTS="-XX:+UseZGC \
                -XX:+UseStringDeduplication \
                -XX:ZCollectionInterval=60 \
                -XX:ZFragmentationLimit=15 \
                -XX:ZUncommitDelay=60 \
                -XX:+ZGenerational \
                -XX:+UseCompressedOops \
                -Xms4G \
                -Xmx7G \
                -XX:+UseContainerSupport \
                -XX:MaxRAMPercentage=80.0 \
                -XX:+UseStringDeduplication \
                -Xlog:gc*:file=gc.log:time,uptime,level,tags"

WORKDIR /app
COPY /build/libs/qp-assessment-*-RELEASE.jar report.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "report.jar"]
