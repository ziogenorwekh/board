package com.portfolio.boardproject.api;

import com.portfolio.boardproject.domain.User;
import com.portfolio.boardproject.security.AuthService;
import com.portfolio.boardproject.security.CustomUserDetails;
import com.portfolio.boardproject.security.CustomUserDetailsService;
import com.portfolio.boardproject.security.JwtProvider;
import com.portfolio.boardproject.vo.LoginResponseVO;
import com.portfolio.boardproject.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {

    private final AuthService authService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthenticationResource(AuthService authService, CustomUserDetailsService customUserDetailsService) {
        this.authService = authService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseVO> login(@RequestBody LoginVO loginVO) {
        LoginResponseVO loginResponseVO = authService.login(loginVO);
        return ResponseEntity.ok(loginResponseVO);
    }

    @RequestMapping(path = "/mail", method = RequestMethod.POST)
    public ResponseEntity<Void> mailSend(@RequestParam("email") String email) {
        User user = customUserDetailsService.findByEmail(email);
        authService.sendMail(user);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/verify/mail",method = RequestMethod.PUT)
    public ResponseEntity<Void> verifyMail(@RequestParam("code") String code, @RequestParam("email") String email) {
        Boolean verified = authService.verifyEmail(email, code);
        if (verified) {
            customUserDetailsService.activateUser(email);
            return ResponseEntity.ok().build();
        } else {
            throw new BadCredentialsException("Invalid verification code");
        }
    }

}
