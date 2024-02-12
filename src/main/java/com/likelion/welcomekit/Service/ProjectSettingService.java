package com.likelion.welcomekit.Service;

import com.likelion.welcomekit.Domain.Entity.ProjectSetting;
import com.likelion.welcomekit.Domain.Entity.User;
import com.likelion.welcomekit.Exception.AnyExceptionsWithResponse;
import com.likelion.welcomekit.Repository.LetterRepository;
import com.likelion.welcomekit.Repository.ProjectSettingRepository;
import com.likelion.welcomekit.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectSettingService {
    private final ProjectSettingRepository projectSettingRepository;
    private final UserRepository userRepository;
    private final LetterRepository letterRepository;

    public void initialProjectSettingDB(){
        if (projectSettingRepository.findById(0L).isPresent()){
            return;
        }
        ProjectSetting projectSetting = new ProjectSetting();
        projectSettingRepository.save(projectSetting);
    }

    public void setManitoActive(boolean isManitoActive){
        ProjectSetting setting = projectSettingRepository.findById(0L)
                .orElseThrow(() -> new EntityNotFoundException("기본 Project Setting"));
        setting.setManitoActive(isManitoActive);
        projectSettingRepository.save(setting);
    }

    public void setManitoFinished(boolean isManitoFinished){
        ProjectSetting setting = projectSettingRepository.findById(0L)
                .orElseThrow(() -> new EntityNotFoundException("기본 Project Setting"));
        setting.setManitoFinished(isManitoFinished);
        projectSettingRepository.save(setting);
    }


    // 마니또 활성화 여부 반환
    public boolean getProjectSettingDB(){
        return projectSettingRepository.findById(0L)
                .orElseThrow(() -> new EntityNotFoundException("기본 Project Setting"))
                .isManitoActive();
    }

    public String makeManito(){
        if (getProjectSettingDB()){
            throw new AnyExceptionsWithResponse("이미 시작되었습니다");
        }
        // 마니또 시작 설정
        setManitoActive(true);

        // User들을 랜덤으로 마니또 연결지음
        List<User> users = userRepository.findAll().stream()
                .filter(user -> !user.isManitoDisabled())
                .collect(Collectors.toList());
        Collections.shuffle(users);

        // 마지막 유저는 첫 번째 유저를 매니토로 할당
        final int size = users.size();
        for (int i = 0; i < size; i++) {
            User currentUser = users.get(i);
            User manitoTo = users.get((i + 1) % size); // 순환 구조를 만들기 위해 나머지 연산 사용

            currentUser.setManitoTo(manitoTo);
            manitoTo.setManitoFrom(currentUser);
        }

        userRepository.saveAll(users);
        return "성공적으로 마니또가 설정되었습니다";
    }

    public String stopManito(){
        if (!getProjectSettingDB()){
            throw new AnyExceptionsWithResponse("이미 종료되었습니다");
        }
        // 마니또 종료 설정
        // User Service에서 마니또 결과 공개
        setManitoFinished(true);
        return "성공적으로 종료되었습니다. 이제 서로의 마니또를 확인할 수 있습니다.";
    }

    public String resetManito(){
        // 마니또 생성 전으로 되돌리기. 버그 발생 또는 유사 시 대응을 위함
        setManitoActive(false);
        setManitoFinished(false);
        // User들을 랜덤으로 마니또 연결지음
        List<User> users = userRepository.findAll();

        // 마지막 유저는 첫 번째 유저를 매니토로 할당
        final int size = users.size();
        for (int i = 0; i < size; i++) {
            User currentUser = users.get(i);
            User manitoTo = users.get((i + 1) % size);

            currentUser.setManitoTo(null);
            manitoTo.setManitoFrom(null);
        }
        userRepository.saveAll(users);
        letterRepository.deleteByIsWelcomeFalse();
        return "마니또 하드 리셋 완료";
    }
}
