package com.toy.chustnut.controller;

import com.toy.chustnut.domain.ProductsSaveRequestDto;
import com.toy.chustnut.service.CommentsService;
import com.toy.chustnut.service.ProductsService;
import com.toy.chustnut.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final S3Service s3Service;
    private final ProductsService productsService;
    private final CommentsService commentsService;

    @PostMapping(value = "/products/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    // @RequestPart는 멀티파트의 'Content-Type' 헤더를 기억하도록 HttpMessageConverter로 전달된 멀티파트의 내용을 가질 수 있게 한다.
    // @RequestPart는 'Content-Type' 헤더에 기반해서 JSON 컨텐츠로 읽어서 MappingJacksonHttpMessageConverter로 변환한다.
    public Long save(@RequestPart("Data") ProductsSaveRequestDto productsSaveRequestDto, @RequestPart("image") MultipartFile image, HttpServletRequest request) throws IOException {

        System.out.println("productsRequestDto : " + productsSaveRequestDto);
        System.out.println("requestDto.title : " + productsSaveRequestDto.getTitle());
        System.out.println("image.getName : " + image.getOriginalFilename());
        System.out.println("image.getSize : " + image.getSize());

        if (image.getSize() > 5000000) throw new IllegalStateException("Basic Error");

        String newName = getNewImageName(image.getOriginalFilename());
        MultipartFile imageToUpload = getNewFile(newName, image);

        String imagePath = s3Service.upload(imageToUpload);
        System.out.println("s3 upload access, imagePath : " + imagePath);
        productsSaveRequestDto.setImageFilePath(imagePath);

        return productsService.save();
    }

    private String getNewImageName(String originalFilename) {

        int dotIndex = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(dotIndex);
        SimpleDateFormat postFix = new SimpleDateFormat("yyyyMMdd-HHmmssSSS");
        int rand = (int) (Math.random() * 1000);

        return originalFilename.substring(0, dotIndex) + postFix.format(System.currentTimeMillis()) + "_" + rand + ext;
    }

    // 스프링에서 제공하는 MultipartFile 이라는 인터페이스를 이용해서, HTTP multipart 요청을 처리한다.
    // MultipartFile 요청은 큰 파일을 청크 단위로 쪼개서 효율적으로 파일 업로드 할 수 있게 해준다.
    public MultipartFile getNewFile(String fileName, MultipartFile currentFile) {

        return new MultipartFile() {
            @Override
            public String getName() {
                return currentFile.getName();
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return currentFile.getContentType();
            }

            @Override
            public boolean isEmpty() {
                return currentFile.isEmpty();
            }

            @Override
            public long getSize() {
                return currentFile.getSize();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return currentFile.getBytes();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return currentFile.getInputStream();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
            }
        };
    }
}


