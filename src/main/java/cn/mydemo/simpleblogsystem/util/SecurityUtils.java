package cn.mydemo.simpleblogsystem.util;

import cn.mydemo.simpleblogsystem.security.JwtAuthenticationToken;
import cn.mydemo.simpleblogsystem.security.JwtUserDetails;
import cn.mydemo.simpleblogsystem.security.MyUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Security相关操作
 *
 */
public class SecurityUtils {

    /**
     * 系统登录认证
     *
     * @param request
     * @param username
     * @param password
     * @return
     */
    public static JwtAuthenticationToken login(HttpServletRequest request, String username, String password,AuthenticationManager authenticationManager) {

        System.out.println("----------JwtAuthenticationToken------------------------------");

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");

        grantedAuthorities.add(grantedAuthority);


        JwtAuthenticationToken token = new JwtAuthenticationToken(username, password ,grantedAuthorities);

        System.out.println("1、token:" + token);

        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        System.out.println("2、token:" + token);


        try{
            Authentication authentication = authenticationManager.authenticate(token);

            System.out.println("authentication:" + authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            token.setToken(cn.mydemo.simpleblogsystem.util.JwtTokenUtils.generateToken(authentication));


            System.out.println("final token:" + token);


        }catch (AuthenticationException e){
            e.printStackTrace();
        }

        System.out.println("----------JwtAuthenticationToken------------------------------");

        return token;

    }

    /**
     * 获取令牌进行认证
     *
     * @param request
     */
    public static void checkAuthentication(HttpServletRequest request) {
        // 获取令牌并根据令牌获取登录认证信息
        Authentication authentication = cn.mydemo.simpleblogsystem.util.JwtTokenUtils.getAuthenticationeFromToken(request);
        // 设置登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getUsername() {
        String username = null;
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUsername(Authentication authentication) {
        String username = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }

    /**
     * 获取当前登录信息
     *
     * @return
     */
    public static Authentication getAuthentication() {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

}
