package com.elisio.desafioItau.framework.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsCloudConfig {

    @Value("${spring.cloud.aws.endpoint}")
    private String host;

    @Value("${spring.cloud.aws.credentials.access-key}")
    protected String accessKeyId;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    protected String secretAccessKey;

    @Value("${spring.cloud.aws.region.static}")
    protected String region;

    @Bean
    public AmazonSNSAsync amazonSNSAsync() {
        return AmazonSNSAsyncClientBuilder.standard()
                .withEndpointConfiguration(getEndpointConfiguration())
                .withCredentials(getCredentialsProvider())
                .build();
    }


    private AWSStaticCredentialsProvider getCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey));
    }

    private EndpointConfiguration getEndpointConfiguration() {
        return new EndpointConfiguration(host, region);
    }

}
