#!/usr/bin/env bash
cd ~/Tools/kafka_2.11-0.8.2-beta
terminal -e "bin/zookeeper-server-start.sh config/zookeeper.properties"
terminal -e "bin/kafka-server-start.sh config/server.properties"

sleep 16

terminal -e "bin/kafka-server-start.sh config/server2.properties"

zookeeper="--zookeeper localhost:2181"
create="--create $zookeeper --replication-factor 3 --partitions 3"

bin/kafka-topics.sh $create --topic make-transfer
bin/kafka-topics.sh $create --topic status-sender

sleep 16

brokers="--broker-list localhost:9092,localhost:9093,localhost:9094"
terminal -e "bin/kafka-console-producer.sh  $brokers --topic make-transfer"
terminal -e "bin/kafka-console-producer.sh  $brokers --topic status-sender"

terminal -e "bin/kafka-console-consumer.sh --topic make-transfer --bootstrap-server localhost:9092 --from-beginning"
terminal -e "bin/kafka-console-consumer.sh --topic status-sender --bootstrap-server localhost:9092 --from-beginning"