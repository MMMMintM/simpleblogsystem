package cn.mydemo.simpleblogsystem.controller;
import cn.mydemo.simpleblogsystem.bean.LoginResult;
import cn.mydemo.simpleblogsystem.bean.RegisterResult;
import cn.mydemo.simpleblogsystem.bean.User;
import cn.mydemo.simpleblogsystem.http.HttpResult;
import cn.mydemo.simpleblogsystem.security.JwtAuthenticationToken;
import cn.mydemo.simpleblogsystem.service.UserService;
import cn.mydemo.simpleblogsystem.util.JwtTokenUtils;
import cn.mydemo.simpleblogsystem.util.PasswordEncoder;
import cn.mydemo.simpleblogsystem.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordVerify;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("register")
    public  HttpResult Register(@RequestParam String username,@RequestParam String password,@RequestParam String email){

        RegisterResult registerResult = new RegisterResult(false,false,false);


        if(userService.isUsernameExist(username)){
            System.out.println("用户名存在");
            registerResult.setUsernameError(true);
        }

        if(userService.isEmailExist(email)){
            System.out.println("邮箱存在");
            registerResult.setEmailError(true);
        }

        /**
         *若username或email中任意有误，返回false
         * 返回registerResult对象，用于告知前端错误内容是username还是emial
         */
        if (registerResult.getUsernameError() == true || registerResult.getEmailError() == true)
            return HttpResult.ok(registerResult);

        List<String> encResult = passwordVerify.passwordEnc(password);

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encResult.get(1));
        user.setSalt(encResult.get(0));

        userService.register(user);

        registerResult.setResult(true);


        return HttpResult.ok(registerResult);
    }


    /**
     * 用户登录使用security的jwt实现
     * @return
     */
    @PostMapping("login")
    public  HttpResult Login(@RequestParam String username, @RequestParam String password, HttpServletRequest request){

        if(!userService.isUsernameExist(username)){
            return HttpResult.ok("用户名不存在");
        }

        LoginResult loginResult = userService.loginRequestForm(username);
        String passwordWithSalt = password + loginResult.getSalt();
        String encoderedPass = loginResult.getPassword();

        loginResult.setResult(passwordVerify.loginVerify(passwordWithSalt,encoderedPass));

        if(loginResult.getResult() == false){

            String msg = "pass error";
            return HttpResult.error(msg);
        }

        JwtAuthenticationToken token = SecurityUtils.login(request, username ,password,authenticationManager);

        return HttpResult.ok(token);
    }

    @GetMapping("me")
    public  HttpResult Me(HttpServletRequest request){

        String token = request.getHeader("token");

        String username = JwtTokenUtils.getUsernameFromToken(token);

        User user = userService.me(username);

        return HttpResult.ok(user);

    }
}
