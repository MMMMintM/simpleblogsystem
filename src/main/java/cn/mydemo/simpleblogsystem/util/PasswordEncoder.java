package cn.mydemo.simpleblogsystem.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Component
public class PasswordEncoder {

    /**
     * 使用uuid和securtiy的bcrytpasswordencoder对密码进行加密
     * @param password
     * @return
     */

    public List<String> passwordEnc(String password){
        List encResult = new ArrayList();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String salt = UUID.randomUUID().toString();

        String encoderedPassword = passwordEncoder.encode(password + salt);

        encResult.add(salt);

        encResult.add(encoderedPassword);

        return encResult;
    }


    public boolean loginVerify(String passwordWithSalt,String encoderedPassword){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println("---------loginVerify-----------");

        boolean result;

        System.out.println("passwordWithSalt" + passwordWithSalt);
        System.out.println("encoderedPassword" + encoderedPassword);

        result = passwordEncoder.matches(passwordWithSalt,encoderedPassword);

        System.out.println("result:" + result);

        System.out.println("---------loginVerify-----------");
        return result;
    }

}
