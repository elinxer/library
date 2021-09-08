#!/bin/sh
# T+1日凌晨 00:30 开始清除10天前的日志
# 30 * * * * /bin/sh /home/weblogic/jnbank_rdc/bin/auto_delete_log.sh >> /home/weblogic/jnbank_rdc/logs/auto_delete_log.log >&1
LOG_HOME=/home/weblogic/user_projects/domains/jnbank/logs/
find $LOG_HOME -mtime +10 -name jnbank_rdc*.txt.*  -exec rm -rf {} \;
echo "日志清理完成!"