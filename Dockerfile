FROM amazoncorretto:11-alpine

EXPOSE 8080

RUN mkdir -p /aws && \
    apk -Uuv --no-cache add groff less python3 py3-pip curl && \
	pip3 install --no-cache-dir awscli

RUN mkdir -p /apps/startup/

RUN addgroup -g 1000 blog_user
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home /apps \
    --ingroup blog_user \
    --no-create-home \
    --uid 1001 \
    blog_user

COPY infrastructure/startup.sh /apps/startup/

ARG APPNAME
ENV APPNAME=$APPNAME
ARG VERSION
ENV VERSION=$VERSION

COPY target/$APPNAME-$VERSION.jar /apps/sb/

RUN chmod +x /apps/startup/startup.sh
RUN chown blog_user:blog_user -R /apps

USER blog_user
WORKDIR /apps

ENTRYPOINT ["./startup/startup.sh"]
