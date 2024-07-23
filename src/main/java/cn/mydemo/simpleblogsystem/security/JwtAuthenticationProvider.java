package cn.mydemo.simpleblogsystem.security;

import cn.mydemo.simpleblogsystem.util.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 身份验证提供者
 */
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

/*    public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
    }*/

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        System.out.println("---------JwtAuthenticationProvider-----------");


        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();
        String salt = ((cn.mydemo.simpleblogsystem.security.JwtUserDetails) userDetails).getSalt();

        System.out.println("presentedPassword:" + presentedPassword);

        System.out.println("userDetails.getPassword():" + userDetails.getPassword());

        System.out.println("salt" + salt);

        System.out.println("重写的验证：" + new PasswordEncoder().loginVerify(presentedPassword + salt, userDetails.getPassword()));

        // 覆写密码验证逻辑
/*        if (!new PasswordEncoder(salt).matches(presentedPassword + salt, userDetails.getPassword())) {


            logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }*/

        if (!new PasswordEncoder().loginVerify(presentedPassword + salt, userDetails.getPassword())) {


            logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }


        System.out.println("---------JwtAuthenticationProvider-----------");
    }

}