package cn.mydemo.simpleblogsystem.service.impl;

import cn.mydemo.simpleblogsystem.bean.LoginResult;
import cn.mydemo.simpleblogsystem.bean.User;
import cn.mydemo.simpleblogsystem.mapper.UserMapper;
import cn.mydemo.simpleblogsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;

    /**
     * 检查数据库中是否存在此username
     * 存在则返回 1
     * @param username
     * @return
     */
    @Override
    public boolean isUsernameExist(String username) {
        int isExist = 0;
        isExist = userMapper.isUsernameExist(username);

        if(isExist == 1)
            return true;
        return false;
    }


    /**
     * 检查邮箱是否存在
     * 存在则返回 1
     * @param email
     * @return
     */
    @Override
    public boolean isEmailExist(String email) {
        int isExist = 0;

        isExist = userMapper.isEmailExist(email);

        if (isExist == 1)
            return true;

        return false;
    }

    @Override
    public void register(User user) {
        userMapper.register(user);
    }


    @Override
    public LoginResult loginRequestForm(String username) {
        LoginResult loginResult = new LoginResult();


        loginResult = userMapper.loginRequestForm(username);


        return loginResult;

    }

    @Override
    public User me(String username) {

        User user = userMapper.me(username);

        return user;
    }
}
