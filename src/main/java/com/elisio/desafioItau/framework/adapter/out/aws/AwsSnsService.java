package com.elisio.desafioItau.framework.adapter.out.aws;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AwsSnsService {

    private final AmazonSNS snsClient;
    Topic bookTopic;

    public AwsSnsService(AmazonSNS amazonSNS, @Qualifier("bookEventTopic") Topic bookTopic) { //o qualifier esta no awsSns
        this.snsClient = amazonSNS;
        this.bookTopic = bookTopic;
    }

    public void publish(String message) {
        PublishResult publish = this.snsClient.publish(bookTopic.getTopicArn(), message);
        log.info("Mensagem enviada para o topico " + bookTopic.getTopicArn() + " Com a mensagem " + message);
    }


}