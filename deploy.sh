#!/usr/bin/env bash

cd /home/ubuntu/app || return 1
echo "进入目标目录，并构建构建镜像！"

cid=$(docker ps -a |grep "$IMAGE_NAME" | awk '{ print $1}')
if [ "$cid" != "" ]; then
  docker stop "$cid";
  docker rm "$cid";
fi

docker build -t snowflyaway/shadow:latest .

echo "检查是否有运行的容器！"

IMAGE_NAME=shadow
docker run --name "$IMAGE_NAME" -p 8280:8080 -d --link my_mysql:mysql -e JAVA_OPTS="$SHADOW_OPTS" -e DB_USERNAME="$DB_USERNAME" -e DB_PASSWORD="$DB_PASSWORD" snowflyaway/shadow
sleep 5s;

echo "$IMAGE_NAME 部署结束！"
exit 0