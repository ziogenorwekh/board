package com.portfolio.boardproject.security;

import com.portfolio.boardproject.domain.User;
import com.portfolio.boardproject.exception.AlreadyRegisteredException;
import com.portfolio.boardproject.vo.LoginResponseVO;
import com.portfolio.boardproject.vo.LoginVO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.Future;

@Component
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtProvider jwtProvider,
                           JavaMailSender javaMailSender, RedisTemplate<String, String> redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.javaMailSender = javaMailSender;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public LoginResponseVO login(LoginVO loginVO) {
        CustomUserDetails userDetails = null;
        String token = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVO.getUsername(), loginVO.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            userDetails = (CustomUserDetails) authenticate.getPrincipal();
            token = jwtProvider.createToken(userDetails);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }
        return new LoginResponseVO(userDetails.getId(), token);
    }

    @Async
    @Override
    public Future<String> sendMail(User user) {
        if (user.getEnabled()) {
            throw new AlreadyRegisteredException("User is enabled");
        }
        redisTemplate.delete(user.getEmail());

        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int number = random.nextInt(888888) + 111111;
            code = String.valueOf(number);
        }
        sendActivationEmail(user.getEmail(), code);
        redisTemplate.opsForValue().set(user.getEmail(), code, Duration.ofMinutes(5L));
        return AsyncResult.forValue(code);
    }

    @Override
    public Boolean verifyEmail(String email, String code) {
        String savedCode = redisTemplate.opsForValue().get(email);
        if (savedCode != null && savedCode.equals(code)) {
            return true;
        }
        return false;
    }

    private void sendActivationEmail(String email, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Account Activation");
            helper.setText(buildEmailContent(code), true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new MailSendException("Failed to send email", e);
        }
    }

    private String buildEmailContent(String code) {
        return "<div style=\"font-family: Arial, sans-serif; margin: 20px;\">"
                + "<h2 style=\"color: #333;\">Activate Your Account</h2>"
                + "<p>Thank you for registering with our service. Please use the following code to activate your account:</p>"
                + "<h3 style=\"color: #007bff;\">" + code + "</h3>"
                + "<p>If you did not register for this service, please ignore this email.</p>"
                + "<p>Best regards,<br>Your Company</p>"
                + "</div>";
    }
}
