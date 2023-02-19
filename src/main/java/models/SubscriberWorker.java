package models;

import lombok.NonNull;
import lombok.SneakyThrows;

public class SubscriberWorker implements Runnable {

    private final Topic topic;
    private final TopicSubscriber topicSubscriber;

    public SubscriberWorker(@NonNull Topic topic, @NonNull final TopicSubscriber topicSubscriber) {
        this.topic = topic;
        this.topicSubscriber = topicSubscriber;
    }

    @SneakyThrows
    @Override
    public void run() {
        synchronized (topicSubscriber) {
            do {
                int currOffset = topicSubscriber.getOffset().get();
                while(currOffset == topic.getMessages().size()) {
                    topicSubscriber.wait();
                }

                Message message = topic.getMessages().get(currOffset);
                topicSubscriber.getSubscriber().consume(message);


                topicSubscriber.getOffset().compareAndSet(currOffset, currOffset+1);
            }

            while(true);
        }
    }


    synchronized public void wakeUpIfNeeded() {
        synchronized (topicSubscriber) {
            topicSubscriber.notify();
        }
    }
}
