### logstash demo

```
input {
  tcp {
    port => 5044
    codec => "json"
  }
}
# 分析、过滤插件，可以多个
filter {
  grok {
    match => ["message", "%{TIMESTAMP_ISO8601:logdate}"]
  }
  date {
    match => ["logdate", "yyyy-MM-dd HH:mm:ss.SSS"]
    target => "@timestamp"
  }
}
output {
  elasticsearch {
    hosts => "http://es-master:9200"
    #index => "%{[fields][log_topics]}-%{+YYYY.MM.dd}"
    #document_type => "%{[@metadata][type]}"
    index => "%{index_name}"
  }
}


```