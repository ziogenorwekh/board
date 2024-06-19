package com.portfolio.boardproject.security;

import com.portfolio.boardproject.domain.User;
import com.portfolio.boardproject.vo.LoginResponseVO;
import com.portfolio.boardproject.vo.LoginVO;
import com.portfolio.boardproject.vo.VerifyCodeVO;

import java.util.concurrent.Future;

public interface AuthService {

    LoginResponseVO login(LoginVO loginVO);

    // mail sender
    Future<String> sendMail(User user);

    // mail verify
    Boolean verifyEmail(VerifyCodeVO verifyCodeVO);
}
