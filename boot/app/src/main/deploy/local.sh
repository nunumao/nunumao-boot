#!/bin/bash
PROJECT_NAME="app"
PROJECT_PATH=`dirname ${BASH_SOURCE[0]}`
XMS=""
XMX=""

log(){
  echo "[deploy] "$1
}

while getopts "n:s:x:" opt;do
  case $opt in
    n)
      PROJECT_NAME=$OPTARG
      ;;
    s)
      XMS="-Xms"$OPTARG"m"
      ;;
    x)
      XMX="-Xmx"$OPTARG"m"
      ;;
    \?)
      echo "Invalid option: -$OPTARG"
      ;;
  esac
done

log "deploy project name -> : $PROJECT_NAME"
log "deploy project path -> : $PROJECT_PATH"

# 查找工程 jar 包
PROJECT_FILES=`find $PROJECT_PATH -name "$PROJECT_NAME*.jar"`
if [ -z "$PROJECT_FILES" ]; then
  log "not found file with pattern $PROJECT_NAME*.jar  in $PROJECT_PATH, then exit."
  exit
fi
log "found files --> $PROJECT_FILES"

# 结束已运行的进程
CURRPID=`ps aux | grep $PROJECT_FILES | grep java | awk '{print $2}' `
if [ ! -z "$CURRPID" ];then
    log "kill -9 $CURRPID"
    kill -9 $CURRPID
fi

# 启动工程
if [ ! -d log  ];then
  mkdir log
fi
log "nohup java $XMS $XMX -jar $PROJECT_FILES >$PROJECT_PATH/log/start.log 2>&1 & "
nohup java $XMS $XMX -jar $PROJECT_FILES >$PROJECT_PATH/log/start.log 2>&1 &

log "sleep 5 seconds to check the out logs"
sleep 5
START_PID=`ps aux | grep "$PROJECT_NAME" | grep java `
if [ -z "$START_PID" ];then
    log "start fail "
    exit
fi
log "start success"
