package com.elisio.desafioAWS.framework.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsCloudConfig {

    @Value("${spring.cloud.aws.credentials.access-key}")
    //@Value("${ACCESS_KEY_AWS}")
    private String acesseKeyId;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    //@Value("${SECRET_KEY_AWS}")
    private String secretKey;

    @Value("${spring.cloud.aws.region.static}")
    //@Value("${REGION_AWS}")
    private String region;

    @Value("${spring.cloud.aws.sns.topic.book-topic.arn}")
    //@Value("${ARN_AWS}")
    private String bookTopicArn;


    @Bean
    public AmazonSNS amazonSNS() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(acesseKeyId, secretKey);

        return AmazonSNSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    @Bean(name = "bookEventTopic")
    //se houver novos topicos futuramente criar novos metodos iguais a este com outros @Beans e uma nova arn
    public Topic snsBookTopic() {
        return new Topic().withTopicArn(bookTopicArn);
    }


}
