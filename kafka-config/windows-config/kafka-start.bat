cd C:\Tools\kafka_2.11-2.0.0
start  bin\windows\zookeeper-server-start.bat config\zookeeper.properties
start  bin\windows\kafka-server-start.bat config\server.properties

TIMEOUT /T 24

start  bin\windows\kafka-server-start.bat config\server1.properties
start  bin\windows\kafka-server-start.bat config\server2.properties

TIMEOUT /T 24

call start bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 3 --topic make-transfer
call start bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 3 --topic status-sender

TIMEOUT /T 16

start bin\windows\kafka-console-producer.bat --topic make-transfer --broker-list localhost:9092,localhost:9093,localhost:9094
start bin\windows\kafka-console-producer.bat --topic status-sender --broker-list localhost:9092,localhost:9093,localhost:9094

start bin\windows\kafka-console-consumer.bat --topic make-transfer --bootstrap-server localhost:9092,localhost:9093,localhost:9094 --from-beginning
start bin\windows\kafka-console-consumer.bat --topic status-sender --bootstrap-server localhost:9092,localhost:9093,localhost:9094 --from-beginning


