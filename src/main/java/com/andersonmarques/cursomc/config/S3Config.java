package com.andersonmarques.cursomc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
	
	@Value("${aws.access_key_id}")
	private String awsId;
	
	@Value("${aws.secret_access_key}")
	private String awsKey;
	
	@Value("${s3.region}")
	private String regionName;
	
	@Bean
	public AmazonS3 s3client() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials (awsId, awsKey);
		
		AmazonS3 s3cliente = AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(regionName))
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
		return s3cliente;
	}

}
