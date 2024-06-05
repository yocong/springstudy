package com.study.springstudy.springmvc.upload;

import com.study.springstudy.springmvc.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@Slf4j
public class UploadController {

    // 업로드 루트 경로
    private String rootPath = "C:/Users/smr78/OneDrive/바탕 화면/yocong/spring-prj/upload";

    @GetMapping("/upload/form")
    public String uploadForm() {
        return "upload/upload-form";
    }

    // MultipartFile : 파일 업로드시 필수
    // 파일이 여러개면 List<MultipartFile> 로 처리
    // @RequestParam("thumbnail") : js에서 name부분을 spring에서 file로 받음
    @PostMapping("/upload/file")
    public String uploadFile(
            @RequestParam("thumbnail") MultipartFile file
    ) {
        // 서버에서 전송된 파일의 정보 읽어옴
        log.info("file-name: {}", file.getOriginalFilename());
        log.info("file-size: {}MB", file.getSize() / 1024.0 / 1024.0);
        log.info("file-type: {}", file.getContentType());

        // 첨부파일 서버에 저장하기
        // 1. 루트 디렉토리를 생성
        File root = new File(rootPath);
        if (!root.exists()) root.mkdirs();

        // 2. 원본 파일명을 중복이 없는 랜덤 파일명으로 변경하여 파일 업로드
        FileUtil.uploadFile(rootPath, file);

        return "redirect:/upload/form";
    }


}
