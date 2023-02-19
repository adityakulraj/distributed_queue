# distributed_queue
This is a Low Lewel Design of Implemented a Distributed Queue in a pub-sub fashion which is done by the likes of Kafka, RabbitMQ etc. 


#APIs supported in queue

createTopic(topicName) -> topicId

subscribe(topicId, subscriber) -> boolean

publish(topicId, message) -> boolean

readOffset(topidId, subscriber, offset) -> boolean
