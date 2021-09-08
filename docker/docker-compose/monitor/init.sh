#!/bin/sh

mkdir -pv /data/docker_file/prometheus/data
mkdir -pv /data/docker_file/prometheus/conf
mkdir -pv /data/docker_file/grafana/data
mkdir -pv /data/docker_file/grafana/log
chmod 777 /data/docker_file/prometheus/data
chmod 777 /data/docker_file/prometheus/conf
chmod 777 /data/docker_file/grafana/data
chmod 777 /data/docker_file/grafana/log
touch /data/docker_file/prometheus/conf/prometheus.yml
cat> /data/docker_file/prometheus/conf/prometheus.yml <<EOF
global:
  scrape_interval:     15s
  evaluation_interval: 15s

scrape_configs:
  - job_name: 'prometheus'
    static_configs:
      - targets: ['prometheus:9090']
  - job_name: 'node'
    scrape_interval: 8s
    static_configs:
      - targets: ['node-exporter:9100']
  - job_name: 'spring-boot-node'
    scrape_interval: 5s
    metrics_path: "/actuator/prometheus"
    static_configs:
      - targets: ['192.168.3.101:8080']
EOF
vim /data/docker_file/prometheus/conf/prometheus.yml