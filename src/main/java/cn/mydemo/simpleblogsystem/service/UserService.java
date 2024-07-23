package cn.mydemo.simpleblogsystem.service;


import cn.mydemo.simpleblogsystem.bean.LoginResult;
import cn.mydemo.simpleblogsystem.bean.User;
import org.springframework.stereotype.Service;

public interface UserService {

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    void register(User user);

    LoginResult loginRequestForm(String username);

    User me(String username);

}
