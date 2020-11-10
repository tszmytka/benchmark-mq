#!/usr/bin/env sh

artifact="${APP_NAME}_${SCALA_VER}-${APP_VER}"
archive="${artifact}.tgz"

mkdir /tmp/$artifact && cd /tmp/$artifact || exit
wget -O $archive "https://www.apache.org/dyn/closer.cgi?action=download&filename=/$APP_NAME/$APP_VER/$archive"
tar -zxf $archive
mv $artifact /etc
ln -s /etc/$artifact /etc/$APP_NAME

# Clean up
rm -rf /tmp/$artifact
