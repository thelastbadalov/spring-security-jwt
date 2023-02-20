package az.company.springbootjwt.api;

import az.company.springbootjwt.auth.TokenManager;
import az.company.springbootjwt.request.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
private final TokenManager manager;

    public AuthController(TokenManager manager, AuthenticationManager authenticationManager) {
        this.manager = manager;
        this.authenticationManager = authenticationManager;
    }

private final AuthenticationManager authenticationManager;


@PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest request){

    try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword()));
        return ResponseEntity.ok(manager.generateToken(request.getUsername()));
    }catch (Exception ex){
        throw  ex;
    }
}

}
