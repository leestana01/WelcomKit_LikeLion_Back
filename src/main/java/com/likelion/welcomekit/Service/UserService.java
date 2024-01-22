package com.likelion.welcomekit.Service;

import com.likelion.welcomekit.Domain.DTO.UserJoinDTO;
import com.likelion.welcomekit.Domain.DTO.UserMyInfoDTO;
import com.likelion.welcomekit.Domain.DTO.UserTeammateResponseDTO;
import com.likelion.welcomekit.Domain.Entity.User;
import com.likelion.welcomekit.Domain.Types;
import com.likelion.welcomekit.Exception.*;
import com.likelion.welcomekit.Repository.UserRepository;
import com.likelion.welcomekit.Utils.JwtTokenProvider;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public void createUser(UserJoinDTO userJoinDTO){
        userRepository.findByName(userJoinDTO.getName())
                .ifPresent(found -> {
                    throw new EntityExistsException(userJoinDTO.getName());
                });

        User newUser = User.builder()
                .name(userJoinDTO.getName())
                .password(encoder.encode(userJoinDTO.getPassword()))
                .department(userJoinDTO.getDepartment())
                .part(userJoinDTO.getPart())
                .userType(userJoinDTO.getUserType())
                .isTeamLeader(false)
                .teamMessage("")
                .currier(
                        userJoinDTO.getUserType().equals(Types.UserType.ROLE_USER)
                                ? "12기 아기사자"
                                : "11기 아기사자/12기 운영진"
                )
                .teamId(userJoinDTO.getTeamId())
                .manitoTo(null)
                .manitoFrom(null)
                .build();

        // 팀의 유일한 Manager이면 팀장 처리
        if (
                !newUser.getUserType().equals(Types.UserType.ROLE_USER)
                && userRepository.findByTeamIdAndIsTeamLeaderTrue(newUser.getTeamId()).isEmpty()
        ){
            newUser.setTeamLeader(true);
        }

        userRepository.save(newUser);
    }

    public String loginUser(String name, String password){
        User selectedUser = userRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(name));
        if(!encoder.matches(password, selectedUser.getPassword())){
            throw new InvalidPasswordException(name);
        }

        return JwtTokenProvider.createToken(
                selectedUser.getId(),selectedUser.getUserType().toString());
    }

    // ----------------------------------------------
    public Map<String, List<UserTeammateResponseDTO>> getMyTeammates(Long userId){
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(userId.toString()));

        List<User> teammates = userRepository.findByTeamId(selectedUser.getTeamId());

        return teammates.stream()
                .map(this::toTeammateResponseDTO)
                .collect(Collectors.groupingBy(user ->
                        user.getUserType() == Types.UserType.ROLE_USER ? "users" : "managers"));
    }

    public UserMyInfoDTO getMyInfo(Long userId){
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId.toString()));

        return new UserMyInfoDTO(selectedUser.getName(), selectedUser.getUserType());
    }

    public String uploadProfileImage(Long userId, MultipartFile imageFile){
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId.toString()));

        String imageUrl = handleUploadImage(imageFile);

        // 데이터베이스에 이미지 URL 정보 업데이트
        selectedUser.setProfileMiniUrl(imageUrl);
        userRepository.save(selectedUser);
        return imageUrl;
    }

    public String uploadProfileMiniImage(Long userId, MultipartFile imageFile){
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId.toString()));

        String imageUrl = handleUploadImage(imageFile);

        // 데이터베이스에 이미지 URL 정보 업데이트
        selectedUser.setProfileMiniUrl(imageUrl);
        userRepository.save(selectedUser);
        return imageUrl;
    }

    private String handleUploadImage(MultipartFile imageFile){
        // 랜덤 파일 이름 생성
        String originalFileName = imageFile.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new AnyExceptionsWithResponse("파일 이름이 비어서는 안됩니다.");
        }
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String randomFileName = UUID.randomUUID().toString() + fileExtension;

        // 이미지 파일 저장
        String folderPath = "/images"; // 이미지를 저장할 폴더 경로
        Path directoryPath = Paths.get(folderPath);
        Path filePath = directoryPath.resolve(randomFileName);

        try {
            // 폴더 생성
            Files.createDirectories(directoryPath);
            // 파일 저장
            Files.copy(imageFile.getInputStream(), filePath);
        } catch (IOException e) {
            throw new AnyExceptionsWithResponse("요청 처리 중 오류가 발생했습니다.");
        }

        // 이미지 URL 생성
        return "/images/" + randomFileName;
    }

    // ----------------------------------------------

    public void updateTeamMessage(Long managerId, String message){
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException(managerId.toString()));

        if (manager.getUserType().equals(Types.UserType.ROLE_USER)){
            throw new EntityNotManagerException(managerId);
        }

        manager.setTeamMessage(message);
        userRepository.save(manager);
    }

    public void updateTeamLeader(Long managerId){
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException(managerId.toString()));

        if (manager.getUserType().equals(Types.UserType.ROLE_USER)){
            throw new EntityNotManagerException(managerId);
        }

        User teamLeader = userRepository.findByTeamIdAndIsTeamLeaderTrue(manager.getTeamId())
                .orElseThrow(InvalidDBException::new);
        if (manager == teamLeader){
            return;
        }

        teamLeader.setTeamLeader(false);
        manager.setTeamLeader(true);

        userRepository.saveAll(Arrays.asList(manager, teamLeader));
    }

    // -------

    private UserTeammateResponseDTO toTeammateResponseDTO(User user){
        return UserTeammateResponseDTO.builder()
                .name(user.getName())
                .department(user.getDepartment())
                .part(user.getPart())
                .userType(user.getUserType())
                .isTeamLeader(user.isTeamLeader())
                .teamMessage(user.getTeamMessage())
                .currier(user.getCurrier())
                .build();
    }
}
