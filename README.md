# PanRead

> chrome 插件----百度网盘媒体阅读服务，目前支持html，mp3，pdf 等媒体文件，在你点击后，记录已经阅读状态。


注意代码里面的路径是针对我的网盘的，如果自己的网盘请自行修改。

koa2 server -e --ejs

npm install forever -g

forever start bin/www
forever stopall

/usr/local/nginx/conf/vhost 

location ^~ /api/ {
    proxy_pass http://127.0.0.1:3000;      
}
lnmp nginx reload


/volume1/cat-share/baidu/PanRead/server

群晖
/etc/nginx/app.d
sudo synoservicecfg --restart nginx


https://github.com/based2/hibernate-generic-dao/blob/master/dao/src/main/java/com/googlecode/genericdao/dao/DAOUtil.java

ss  下载
https://github.com/shadowsocks/shadowsocks-windows/releases/tag/4.3.2.0


安装
5：wget --no-check-certificate https://raw.githubusercontent.com/teddysun/shadowsocks_install/master/shadowsocksR.sh && chmod +x shadowsocksR.sh

6：./shadowsocksR.sh
