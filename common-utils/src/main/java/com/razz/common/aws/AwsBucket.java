package com.razz.common.aws;

import java.io.File;
import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class AwsBucket {

	private final AmazonS3 amazonS3;
	private final String bucket;

	public AwsBucket(AwsConfig awsConfig) {
		final String accessKey = awsConfig.getString(AwsConfigKey.ACCESS_KEY);
		final String secretKey = awsConfig.getString(AwsConfigKey.SECRET_KEY);
		final String region = awsConfig.getString(AwsConfigKey.REGION);
		bucket = awsConfig.getString(AwsConfigKey.BUCKET);
		final BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey); 
		final AWSStaticCredentialsProvider awsProvider = new AWSStaticCredentialsProvider(awsCreds);
		this.amazonS3 = AmazonS3ClientBuilder.standard()
				.withCredentials(awsProvider)
				.withRegion(region)
				.build();
	}
	
	public List<S3ObjectSummary> list() {
		final ObjectListing result = amazonS3.listObjects(bucket);
		final List<S3ObjectSummary> list = result.getObjectSummaries();
		return list;
	}
	
	public PutObjectResult save(File file) {
		final String keyName = getKeyName(file);
		return save(keyName, file);
	}
	
	public PutObjectResult save(String keyName, File file) {
		final PutObjectRequest request = new PutObjectRequest(bucket, keyName, file);
		final PutObjectResult result = amazonS3.putObject(request);
		return result;
	}
	
	public void delete(File file) {
		final String filename = getKeyName(file);
		delete(filename);
	}
	
	public void delete(String keyName) {
		final DeleteObjectRequest request = new DeleteObjectRequest(bucket, keyName);
		amazonS3.deleteObject(request);
	}
	
	public static String getKeyName(File file) {
		return file.getName();
	}
	
}
