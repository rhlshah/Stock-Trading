package com.stocktrading.platform.Service;

import com.stocktrading.platform.config.UserInfoUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService {

    public String getEmail(){
        UserInfoUserDetails userObj = (UserInfoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userObj.getUsername();
    }

    public boolean userLoggedIn(){
        boolean status =  SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken);
        return status;
    }

    public boolean isAdmin(){
        UserInfoUserDetails userObj = (UserInfoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userObj.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

}
