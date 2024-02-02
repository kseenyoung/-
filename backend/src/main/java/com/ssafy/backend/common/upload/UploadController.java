package com.ssafy.backend.common.upload;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.common.response.BaseResponseStatus;
import com.ssafy.backend.common.utils.S3Uploader;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.ssafy.backend.common.response.BaseResponseStatus.INVALID_AUTH_TOKEN;
import static com.ssafy.backend.common.response.BaseResponseStatus.SUCCESS;

@RestController
@RequestMapping("/upload")
@Slf4j
@RequiredArgsConstructor
public class UploadController {
    private final S3Uploader s3Uploader;
    private final UserService userService;
    @PostMapping("/profile")
    public BaseResponse<?> uploadProfile(@RequestParam("file") MultipartFile file,
                                         HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        if(session == null) throw new BaseException(INVALID_AUTH_TOKEN);
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();
        log.info("file Size : {}",file.getSize());
        User existUser = userService.isExistUser(userId);
        String url = null;
        url = s3Uploader.uploadFile(file, userId);
        userService.saveProfile(existUser,url);
        return new BaseResponse<>(SUCCESS);
    }

}
