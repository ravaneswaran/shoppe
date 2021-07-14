package com.mnrc.services.impl;

import com.mnrc.models.User;
import com.mnrc.models.UserProfile;
import com.mnrc.repositories.UserProfileRepository;
import com.mnrc.repositories.UserRepository;
import com.mnrc.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserProfile saveProfilePicture(String userId, byte[] profilePicture) {

        String uuid = UUID.randomUUID().toString();
        User user = this.userRepository.findById(userId).get();

        UserProfile userProfile = this.userProfileRepository.findByUserUUID(userId);

        if(null == userProfile){
            Date now = new Date();
            userProfile = new UserProfile();
            userProfile.setUUID(uuid);
            userProfile.setProfilePicture(profilePicture);
            userProfile.setCreatedDate(now);
            userProfile.setModifiedDate(now);
            userProfile.setUser(user);
        } else {
            Date now = new Date();
            userProfile.setProfilePicture(profilePicture);
            userProfile.setModifiedDate(now);
        }

        return this.userProfileRepository.save(userProfile);
    }
}