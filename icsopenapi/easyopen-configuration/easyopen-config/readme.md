# 配置中心服务端


## 启动顺序

先启动配置中心，再启接口项目。

关闭进程建议使用kill -12 pid

简单重启脚本`restart.sh`

```
echo "Stopping app"
pid=`ps -ef | grep 你的app名.jar | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ]
then
   echo "kill -12 的id:" $pid
   kill -12 $pid
fi
# 重新启动
nohup java -jar -Dspring.profiles.active=dev 你的app名.jar &
```

将`你的app名`改成应用名称


