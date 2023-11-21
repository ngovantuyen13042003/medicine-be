package nvt.st.springjwtauthentication.jwt;

import lombok.extern.slf4j.Slf4j;
import nvt.st.springjwtauthentication.security.CustomUserDetail;
import nvt.st.springjwtauthentication.security.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// @Slf4j: using writing log
@Slf4j
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserService customUserService;

    private String getJwtFormRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // check header Authorization contain information jwt
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return  bearerToken.substring(7);
        }
        return null;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            // get JWT from request
            String jwt  = getJwtFormRequest(request);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
                // get username from string JWT
                String email = jwtTokenProvider.getUserNameFromJwt(jwt);
                // get information of user from idUser
                UserDetails userDetails = customUserService.loadUserByUsername(email);
                if(userDetails != null){
                    // if user valid then set information for security context
                    UsernamePasswordAuthenticationToken authentication
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (Exception ex){
            log.error("Failure on set user authorization!");
        }
        filterChain.doFilter(request, response);
    }
}
