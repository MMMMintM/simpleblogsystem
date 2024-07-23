package cn.mydemo.simpleblogsystem.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 安全用户模型
 * @Author Liwei
 * @Date 2021-08-13 17:52
 */
public class JwtUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String username;
    private String password;
    private String salt;

    private List<GrantedAuthority> grantedAuthorities;


    JwtUserDetails(String username, String password, String salt, List<GrantedAuthority> grantedAuthorities) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.grantedAuthorities = grantedAuthorities;

    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    public String getSalt() {
		return salt;
	}
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}