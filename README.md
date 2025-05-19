Description: This is server side app to receive the metrics of %age cpu utilization from hms-agent through web socket via kafka.
Further the metrics are stored in elastic search time series based database. Using Kibana, the metrics can be listed and searched easily.

Install Kafka:
$ brew install kafka
$ brew services start kafka

To set up Elasticsearch and Kibana locally, run the start-local script in the command line:
curl -fsSL https://elastic.co/start-local | sh
After running the script, you can access Elastic services at the following endpoints:

Elasticsearch: http://localhost:9200
Kibana: http://localhost:5601

note:
docker-compose is in progress.