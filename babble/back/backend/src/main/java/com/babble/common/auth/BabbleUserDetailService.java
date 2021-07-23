package com.babble.common.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.babble.api.service.UserService;
import com.babble.db.entity.User;


/**
 * 현재 액세스 토큰으로 부터 인증된 유저의 상세정보(활성화 여부, 만료, 롤 등) 관련 서비스 정의.
 */
@Component
public class BabbleUserDetailService implements UserDetailsService{
	@Autowired
	UserService userService;
	
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    		User user = userService.getUserByUserEmail(email); //usrId를 통해 db에 유저정보있나 확인
    		if(user != null) { //db에 유저가 존재하면
				//현재 액세스 토큰으로 부터 인증된 유저의 부가 상세정보(활성화 여부, 만료, 롤 등) 정의.
    			BabbleUserDetails babbleUserDetails = new BabbleUserDetails(user);
    			return babbleUserDetails;
    		}
    		return null;
    }
}
