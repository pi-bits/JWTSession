package com.session.jwt.security;

import com.session.jwt.model.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private LoginJwtValidator validator;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        LoginAuthenticationToken loginToken = (LoginAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = loginToken.getToken();
        JwtUser jwtUser = validator.validate(token);

        if(jwtUser==null)
        {
            throw new RuntimeException("Incorrect Token");
        }

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());

        return new LoginUserDetails(jwtUser.getUserName(),jwtUser.getRole(),token, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return LoginAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
