FROM dockergarten/payara-micro
LABEL maintainer="Marcus Fihlon, fihlon.ch"
COPY build/libs/contactus.war ${DEPLOYMENT_DIR}
ENV JAVA_MEMORY 64m
HEALTHCHECK --interval=5s --timeout=3s --retries=3 CMD curl -X GET --fail "http://localhost:8080/api/monitoring/ping/pong" || exit 1
