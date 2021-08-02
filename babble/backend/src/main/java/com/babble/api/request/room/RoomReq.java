package com.babble.api.request.room;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ApiModel("RoomRequest")
public class RoomReq {

    MultipartFile multipartFile;
    RoomCreateReq roomCreateReq;
}
