package cn.mydemo.simpleblogsystem.security;

import cn.mydemo.simpleblogsystem.bean.User;
import cn.mydemo.simpleblogsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("MyUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.me(username);

        if (user == null){

            throw new UsernameNotFoundException("USER UNEXIST");

        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");

        grantedAuthorities.add(grantedAuthority);

        return new cn.mydemo.simpleblogsystem.security.JwtUserDetails(user.getUsername(),user.getPassword(),user.getSalt(),grantedAuthorities);
    }

}
