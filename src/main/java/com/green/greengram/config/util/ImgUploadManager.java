package com.green.greengram.config.util;

import com.green.greengram.config.constants.ConstFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImgUploadManager {
    private final ConstFile constFile;
    private final MyFileUtils myFileUtils;

    public void saveFeedPics(long feedId, List<MultipartFile> feedPicFiles) {

    }


    //저장한 파일명 리턴
    public String saveProfilePic(long userId, MultipartFile profilePicFile) {
        //폴더 생성
        String directory = String.format("%s/%s/%d", constFile.getUploadDirectory(), constFile.getProfilePic(), userId);
        myFileUtils.makeFolders(directory);

        String randomFileName = myFileUtils.makeRandomFileName(profilePicFile);
        String savePath = directory + "/" + randomFileName;

        try {
            myFileUtils.transferTo(profilePicFile, savePath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "프로파일 이미지 저장에 실패하였습니다.");
        }
        return randomFileName;
    }
}
