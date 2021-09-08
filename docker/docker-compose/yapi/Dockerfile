FROM node:12-alpine as builder
RUN apk add --no-cache git python make openssl tar gcc
COPY yapi.tar.gz /home
RUN cd /home && tar zxvf yapi.tar.gz && mkdir /api && mv /home/yapi-1.9.2 /api/vendors
RUN cd /api/vendors && npm install --production --registry https://registry.npm.taobao.org

FROM node:12-alpine
MAINTAINER 793272861@qq.com
COPY --from=builder /api/vendors /api/vendors
COPY config.json /api/ls
ENV TZ="Asia/Shanghai"
WORKDIR /api/vendors
EXPOSE 3000
CMD ["node", "server/app.js"]