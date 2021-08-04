package com.babble.api.service;

import com.babble.db.entity.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

/**
 *	이미지 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {

    @Autowired
    UserService userService;

    Date date = new Date();

    @Override
    public boolean userImageUpload(String email, MultipartFile fileName) {
        File dest = new File(date.getTime()+fileName.getOriginalFilename());
        try {
            fileName.transferTo(dest);
            //db에 파일명 저장
            userService.updatePicture(email,date.getTime()+fileName.getOriginalFilename());
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public byte[] getUserImage(String email) throws IOException {
        User user = userService.getUserByUserEmail(email);
        System.out.println(user.getPicture());
        InputStream imageStream = new FileInputStream(user.getPicture());
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return imageByteArray;
    }

    @Override
    public String roomImageUpload(MultipartFile fileName) {
        File dest = new File(date.getTime()+fileName.getOriginalFilename());
        try {
            fileName.transferTo(dest);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return date.getTime()+fileName.getOriginalFilename();
    }

    @Override
    public byte[] getRoomImage(String thumbnail) throws IOException {
        InputStream imageStream = new FileInputStream("d://images/room/" + thumbnail);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return imageByteArray;
    }
}
