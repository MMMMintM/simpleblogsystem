package cn.mydemo.simpleblogsystem.mapper;


import cn.mydemo.simpleblogsystem.bean.LoginResult;
import cn.mydemo.simpleblogsystem.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    int isUsernameExist(@Param(value = "username") String username);

    int isEmailExist(@Param(value = "email") String email);

    void register(@Param(value = "user")User user);

    LoginResult loginRequestForm(@Param(value = "username") String username );

    User me(@Param(value = "username") String username);



}
