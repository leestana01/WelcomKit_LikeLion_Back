package com.likelion.welcomekit.Service;

import com.likelion.welcomekit.Domain.DTO.Info.*;
import com.likelion.welcomekit.Domain.DTO.ManitoResponseDTO;
import com.likelion.welcomekit.Domain.DTO.UserJoinDTO;
import com.likelion.welcomekit.Domain.DTO.Login.UserLoginResponseDTO;
import com.likelion.welcomekit.Domain.DTO.Team.UserTeammateResponseDTO;
import com.likelion.welcomekit.Domain.DTO.UserUpdate.UserUpdateDTO;
import com.likelion.welcomekit.Domain.Entity.Letter;
import com.likelion.welcomekit.Domain.Entity.User;
import com.likelion.welcomekit.Domain.Types;
import com.likelion.welcomekit.Exception.*;
import com.likelion.welcomekit.Repository.LetterRepository;
import com.likelion.welcomekit.Repository.UserRepository;
import com.likelion.welcomekit.Utils.JwtTokenProvider;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final LetterRepository letterRepository;
    private final BCryptPasswordEncoder encoder;
    private final ImageService imageService;
    private final ProjectSettingService projectSettingService;

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

    public UserLoginResponseDTO loginUser(String name, String password){
        User selectedUser = userRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(name));
        if(!encoder.matches(password, selectedUser.getPassword())){
            throw new InvalidPasswordException(name);
        }

        return new UserLoginResponseDTO(
                JwtTokenProvider.createToken(selectedUser.getId(), selectedUser.getUserType().toString())
                ,selectedUser.getUserType().toString()
        );
    }

    public void updatePassword(Long userId, String newPassword) {
        userRepository.findById(userId).ifPresent(user -> {
            String encodedPassword = encoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
        });
    }

    // ----------------------------------------------
    public UserMyInfoResponseDTO getMyInfo(Long userId){
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId.toString()));

        return new UserMyInfoResponseDTO(
                selectedUser.getName(),
                selectedUser.getUserType(),
                selectedUser.getProfileUrl(),
                selectedUser.getProfileMiniUrl());
    }

    public UserMyPageResponseDTO getMyPageInfo(Long userId){
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId.toString()));

        return new UserMyPageResponseDTO(
                selectedUser.getName(),
                selectedUser.getTeamId(),
                selectedUser.isTeamLeader(),
                selectedUser.getPart(),
                selectedUser.getDepartment(),

                selectedUser.getTeamMessage(),
                selectedUser.getTeamLeaderMessage(),

                selectedUser.getProfileUrl(),
                selectedUser.getProfileMiniUrl());
    }

    public ManitoResponseDTO getMyManito(Long userId){
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId.toString()));

        String manitoTo = selectedUser.getManitoTo().getName();
        String manitoFrom = projectSettingService.getProjectSettingDB().isManitoFinished() ?
                selectedUser.getManitoFrom().getName() : "?";
        return new ManitoResponseDTO(
                manitoTo,
                manitoFrom
        );
    }

    public Map<String, List<UserTeammateResponseDTO>> getMyTeammates(Long userId){
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(userId.toString()));

        List<User> teammates = userRepository.findByTeamId(selectedUser.getTeamId());

        return teammates.stream()
                .map(this::toTeammateResponseDTO)
                .collect(Collectors.groupingBy(user ->
                        user.getUserType() == Types.UserType.ROLE_USER ? "users" : "managers"));
    }

    public List<UserInfoResponseDTO> getMyTeammatesForManager(Long userId){
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(userId.toString()));

        List<User> teammates = userRepository.findByTeamId(selectedUser.getTeamId());

        return teammates.stream().map(user -> UserInfoResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .department(user.getDepartment())
                .teamId(user.getTeamId())
                .part(user.getPart())
                .isTeamLeader(user.isTeamLeader())
                .userType(user.getUserType())
                .build()).collect(Collectors.toList());
    }

    public List<UserInfoResponseDTO> getBabyLions(Long userId) {
        List<User> users = userRepository.findAllByUserType(Types.UserType.ROLE_USER);
        List<Letter> letters = letterRepository.findBySenderId(userId);

        return users.stream().map(user -> {
            boolean isMessageWritten = letters.stream().anyMatch(letter -> letter.getTargetId().equals(user.getId()));
            return UserInfoResponseDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .department(user.getDepartment())
                    .teamId(user.getTeamId())
                    .part(user.getPart())
                    .isTeamLeader(user.isTeamLeader())
                    .userType(user.getUserType())
                    .isMessageWritten(isMessageWritten)
                    .build();
        }).collect(Collectors.toList());
    }

    public List<UserUpdateDTO> getManagersForUpdate() {
        return userRepository.findByUserTypeNot(Types.UserType.ROLE_USER).stream()
                .map(manager -> new UserUpdateDTO(manager.getId(), manager.getName(), manager.getTeamId(), manager.isTeamLeader()) )
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateTeamAndLeaderInfo(List<UserUpdateDTO> userUpdateDTOs) {
        // 팀별 팀장 수 계산
        Map<Long, Long> teamLeaderCounts = userUpdateDTOs.stream()
                .filter(UserUpdateDTO::isTeamLeader)
                .collect(Collectors.groupingBy(UserUpdateDTO::getTeamId, Collectors.counting()));

        // 팀장이 없거나 2명 이상인 팀이 있는가
        boolean invalidTeamExists = teamLeaderCounts.values().stream().anyMatch(count -> count != 1);
        if (invalidTeamExists) {
            throw new RuntimeException("모든 팀에는 1명의 팀장이 존재해야합니다.");
        }

//        List<User> usersToUpdate = userUpdateDTOs.stream()
//                .map(dto ->
//                        {
//                            User user = userRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException(dto.getId().toString()));
//                            user.setTeamId(dto.getTeamId());
//                            user.setTeamLeader(dto.isTeamLeader());
//                            return user;
//                        }
//                )
//                .collect(Collectors.toList());
//
//        userRepository.saveAll(usersToUpdate);
        userUpdateDTOs.forEach(dto -> {
            User user = userRepository.findById(dto.getId())
                    .orElseThrow(() -> new EntityNotFoundException(dto.getId().toString()));
            user.setTeamId(dto.getTeamId());
            user.setTeamLeader(dto.isTeamLeader());
            userRepository.save(user);
        });
    }

    public UserCountByPartResponseDTO getUserCountByPart() {
        UserCountByPartResponseDTO dto = new UserCountByPartResponseDTO();
        dto.setBabyLions(userRepository.countByUserType(Types.UserType.ROLE_USER));
        dto.setManagers(userRepository.count() - dto.getBabyLions()); // 전체 유저 수에서 ROLE_USER 수를 빼면 나머지는 managers가 됩니다.

        // 각 PartType에 대한 유저 수 계산
        dto.setBack(userRepository.countByPart(Types.PartType.BACK));
        dto.setFront(userRepository.countByPart(Types.PartType.FRONT));
        dto.setDesign(userRepository.countByPart(Types.PartType.DESIGN));
        dto.setDev(userRepository.countByPart(Types.PartType.DEV));

        return dto;
    }
    // ----------------------------------------------
    public String uploadProfileImage(Long userId, MultipartFile imageFile){
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId.toString()));

        // 기존 이미지 파일 삭제
        deleteExistingImage(selectedUser.getProfileUrl());

        String imageUrl = imageService.handleUploadImage(imageFile, 150, 150);

        // 데이터베이스에 이미지 URL 정보 업데이트
        selectedUser.setProfileUrl(imageUrl);
        userRepository.save(selectedUser);
        return imageUrl;
    }

    public String uploadProfileMiniImage(Long userId, MultipartFile imageFile){
        User selectedUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(userId.toString()));

        // 기존 이미지 파일 삭제
        deleteExistingImage(selectedUser.getProfileMiniUrl());

        String imageUrl = imageService.handleUploadImage(imageFile, 580,300);

        // 데이터베이스에 이미지 URL 정보 업데이트
        selectedUser.setProfileMiniUrl(imageUrl);
        userRepository.save(selectedUser);
        return imageUrl;
    }

    private void deleteExistingImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
//            정석인데 의미 없음
//            Path filePath = Paths.get("/images", imageUrl.replace("/images/", ""));
            Path filePath = Paths.get(imageUrl);
            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                // 로그 처리 또는 예외 처리
            }
        }
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

    public void updateManagerInfo(Long managerId, UserMyPageForManagerRequestDTO dto){
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException(managerId.toString()));

        if (manager.getUserType().equals(Types.UserType.ROLE_USER)){
            throw new EntityNotManagerException(managerId);
        }

        manager.setDepartment(dto.getDepartment());
        manager.setTeamId(dto.getTeamId());
        manager.setPart(dto.getPart());
        manager.setTeamMessage(dto.getTeamMessage());
        manager.setTeamLeaderMessage(dto.getTeamLeaderMessage());

        userRepository.save(manager);
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
                .teamLeaderMessage(user.getTeamLeaderMessage())
                .build();
    }
}
