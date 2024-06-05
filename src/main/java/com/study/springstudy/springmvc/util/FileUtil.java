package com.study.springstudy.springmvc.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class FileUtil {

    /**
     * 사용자가 클라이언트에서 파일을 전송했을 때
     * 중복이 없는 새로운 파일명을 생성하여 해당 파일명으로
     * 날짜별 폴더로 업로드하는 메서드
     *
     * @param file - 사용자가 업로드한 파일의 정보객체
     * @param rootPath - 서버에 업로드할 루트 디렉토리 경로
     *                   ex) D:/spring-prj/upload
     * @return - 업로드가 완료되었을 경우 업로드 된 파일의 위치 경로
     *                   ex)  /2024/05/29/djfalsjdflaksjlfdsaj_고양이.jpg
     */
    public static String uploadFile(String rootPath, MultipartFile file) {

        // 원본 파일명을 중복이 없는 랜덤 파일명으로 변경
        String newFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 이 첨부파일을 날짜별로 관리하기 위해 날짜 폴더를 생성
        String newUploadPath = makeDateFormatDirectory(rootPath);

        // 파일 업로드 수행
        try {
            file.transferTo(new File(newUploadPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String makeDateFormatDirectory(String rootPath) {

        // 오늘 날짜 정보를 추출
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        List<String> dateList = List.of(year + "", len2(month), len2(day));

        // ex) rootPath - D:/spring_prj/upload
        String newDirectoryPath = rootPath;

        // newDirectoryPath - D:/spring_prj/upload/2024/06/05
        for (String s : dateList) {
            newDirectoryPath += "/" + s;
            File f = new File(newDirectoryPath);
            if (!f.exists()) f.mkdir();
        }

        return newDirectoryPath;
    }

    // 한자리 수면 앞에 0이 붙게됨
    // ex) 01, 02, 03 ... 10, 11
    private static String len2(int n) {
        return new DecimalFormat("00").format(n);
    }
}
