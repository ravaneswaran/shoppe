package com.shoppe.services.impl;

import com.shoppe.enums.TokenType;
import com.shoppe.models.Token;
import com.shoppe.repositories.TokenRepository;
import com.shoppe.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;


    @Override
    public Token getSignUpVerificationToken() {

        Token token = new Token();
        Date newDate = new Date();

        token.setType(TokenType.SIGNUP_VERIFICATION_TOKEN.toString());
        token.setExpiryTimeInHours(24);
        token.setCreatedDate(newDate);
        token.setModifiedDate(newDate);

        return token;
    }

    @Override
    public Token storeAndGetSignUpVerificationToken(String creatorUUID, String creatorType){
        Token token = this.getSignUpVerificationToken();

        token.setCreatorUUID(creatorUUID);
        token.setCreatorType(creatorType);
        this.tokenRepository.save(token);

        return token;
    }

    @Override
    public Token getSignUpVerificationTokenByUUID(String signUpVerificationTokenUUID) {
        Token token = this.tokenRepository.findById(signUpVerificationTokenUUID).get();
        if(TokenType.SIGNUP_VERIFICATION_TOKEN.toString().equals(token.getType())){
            return token;
        } else {
            return null;
        }
    }
}