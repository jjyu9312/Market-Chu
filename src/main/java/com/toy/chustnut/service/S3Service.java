package com.toy.chustnut.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

public class S3Service {

    /*
    accessKey, secretKey - AWS 계정에 부여된 key 값을 입력합니다. ( IAM 계정 사용 권장 )
    s3.bucket - S3 서비스에서 생성한 버킷 이름을 작성합니다.
    region.static - S3를 서비스할 region 명을 작성합니다. ( 참고 ) 서울은 ap-northeast-2를 작성하면 됩니다.
     */

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return s3Client.getUrl(bucket, fileName).toString();
    }

    public void delete(String s3Url) throws IOException {

        String fileKey = s3Url.substring(s3Url.lastIndexOf("/") + 1);
        System.out.println("file key to delete : " + fileKey);
        s3Client.deleteObject(bucket, fileKey);
    }

    public byte[] download(String s3Url) throws IOException {

        String fileKey = s3Url.substring(s3Url.lastIndexOf("/") + 1);
        System.out.println("file key to download : " + fileKey);
        S3Object file = s3Client.getObject(bucket, fileKey);

        return IOUtils.toByteArray(file.getObjectContent());
    }

}
