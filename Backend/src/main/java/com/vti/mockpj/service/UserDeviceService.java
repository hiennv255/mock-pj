package com.vti.mockpj.service;


import com.vti.mockpj.dto.DeviceInfo;
import com.vti.mockpj.exception.TokenRefreshException;
import com.vti.mockpj.models.RefreshToken;
import com.vti.mockpj.models.UserDevice;
import com.vti.mockpj.repository.UserDeviceRepository;
import com.vti.mockpj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDeviceService {

	@Autowired
    private UserDeviceRepository userDeviceRepository;


    @Autowired
    UserRepository userRepository;

    public Optional<UserDevice> findByUserId(Long userId) {
        return userDeviceRepository.findByUserId(userId);
    }

    public Optional<UserDevice> findByRefreshToken(RefreshToken refreshToken) {
        return userDeviceRepository.findByRefreshToken(refreshToken);
    }

    public UserDevice createUserDevice(DeviceInfo deviceInfo) {
        UserDevice userDevice = new UserDevice();
        userDevice.setDeviceId(deviceInfo.getDeviceId());
        userDevice.setDeviceType(deviceInfo.getDeviceType());
        userDevice.setIsRefreshActive(true);
        return userDevice;
    }

    public void verifyRefreshAvailability(RefreshToken refreshToken) {
        UserDevice userDevice = findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenRefreshException(refreshToken.getToken(), "No device found for the matching token. Please login again"));

        if (!userDevice.getIsRefreshActive()) {
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh blocked for the device. Please login through a different device");
        }
    }

    public void deleteAll()
    {
        userDeviceRepository.deleteAll();
    }

    public void deleteOldDevice(Long userId)
    {
//        Optional<User> optionalUser = userRepository.findById(userId);
//        userDeviceRepository.dele
    }
}
