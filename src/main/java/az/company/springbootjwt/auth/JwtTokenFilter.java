package az.company.springbootjwt.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final TokenManager manager;

    public JwtTokenFilter(TokenManager manager) {
        this.manager = manager;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (authHeader != null && authHeader.contains("Bearer")) {
             token = authHeader.substring(7);
            try {
                username = manager.getUsernameToken(token);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

 if(username !=null && token!=null&&SecurityContextHolder.getContext().getAuthentication()==null){
     if(manager.tokenValidate(token)){
         UsernamePasswordAuthenticationToken upassToken=new UsernamePasswordAuthenticationToken(username,
                 null,new ArrayList<>());
     upassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
SecurityContextHolder.getContext().setAuthentication(upassToken);
     }
 }
filterChain.doFilter(request,response);
    }
}
