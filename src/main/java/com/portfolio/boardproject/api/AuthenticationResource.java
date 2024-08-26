package com.portfolio.boardproject.api;

import com.portfolio.boardproject.domain.User;
import com.portfolio.boardproject.security.AuthService;
import com.portfolio.boardproject.security.CustomUserDetails;
import com.portfolio.boardproject.security.CustomUserDetailsService;
import com.portfolio.boardproject.security.JwtProvider;
import com.portfolio.boardproject.vo.LoginResponseVO;
import com.portfolio.boardproject.vo.LoginVO;
import com.portfolio.boardproject.vo.SendEmailVO;
import com.portfolio.boardproject.vo.VerifyCodeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationResource {

    private final AuthService authService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthenticationResource(AuthService authService, CustomUserDetailsService customUserDetailsService) {
        this.authService = authService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Operation(summary = "Login", description = "Authenticate user and generate JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = {@io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LoginResponseVO.class)),
                    }),
            @ApiResponse(responseCode = "400", description = "Invalid login credentials")
    })
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseVO> login(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Login credentials")
                                                     @RequestBody LoginVO loginVO) {

        log.info("login {}", loginVO);
        LoginResponseVO loginResponseVO = authService.login(loginVO);
        return ResponseEntity.ok(loginResponseVO);
    }

    @Operation(summary = "Send Verification Email", description = "Send verification email to the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid email format")
    })
    @RequestMapping(path = "/send-mail", method = RequestMethod.POST)
    public ResponseEntity<Void> mailSend(@Parameter(description = "User email") @RequestBody SendEmailVO sendEmailVO) {

        log.info("send email {}", sendEmailVO.getEmail());
        User user = customUserDetailsService.findByEmail(sendEmailVO.getEmail());
        authService.sendMail(user);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Verify Email", description = "Verify user's email with the received code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email verified successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid verification code")
    })
    @RequestMapping(path = "/verification/mail",method = RequestMethod.PUT)
    public ResponseEntity<Void> verifyMail(@Parameter(description = "Verification code") @RequestBody VerifyCodeVO verifyCodeVO) {

        log.info("verify email {}", verifyCodeVO.getEmail());
        Boolean verified = authService.verifyEmail(verifyCodeVO);
        if (verified) {
            log.info("activation code is successfully verified.");
            customUserDetailsService.activateUser(verifyCodeVO.getEmail());
            return ResponseEntity.ok().build();
        } else {
            log.error("verification code error");
            throw new BadCredentialsException("Invalid verification code");
        }
    }

}
