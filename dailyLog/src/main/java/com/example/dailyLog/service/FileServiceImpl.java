package com.example.dailyLog.service;

import com.example.dailyLog.exception.imageException.*;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Log
public class FileServiceImpl implements FileService{

    // 이미지 URL생성
    @Override
    public String uploadFile(String uploadPath
            ,String originalFileName,byte[] fileData){

        if (uploadPath == null || uploadPath.trim().isEmpty()) {
            throw new InvalidUploadPath(ImageErrorCode.INVALID_UPLOAD_PATH);
        }

        if (originalFileName == null || !originalFileName.contains(".")) {
            throw new InvalidFileName(ImageErrorCode.INVALID_FILE_NAME);
        }

        if (fileData == null || fileData.length == 0) {
            throw new EmptyFileData(ImageErrorCode.EMPTY_FILE_DATA);
        }

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new FailedDirectoryCreation(ImageErrorCode.FAILED_DIRECTORY_CREATION);
        }

        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        try (FileOutputStream fos = new FileOutputStream(fileUploadFullUrl)) {
            fos.write(fileData);
        } catch (IOException e) {
            throw new FileWriteError(ImageErrorCode.FILE_WRITE_ERROR);
        }
        return savedFileName;
    }


    // 이미지 삭제
    @Override
    public void deleteFile(String profileImageLocation) throws Exception {
        File deleteFile = new File(profileImageLocation); // 절대 경로 설정
        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제했습니다: " + profileImageLocation);
        } else {
            log.info("파일이 존재하지 않습니다: " + profileImageLocation);
        }
    }
}
