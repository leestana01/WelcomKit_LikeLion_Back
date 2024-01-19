package Service;

import Repository.ProjectSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectSettingService {
    private final ProjectSettingRepository projectSettingRepository;

    private void makeManito(){
        // TODO : User들을 랜덤으로 마니또 연결짓기
    }

    private void stopManito(){
        // TODO : 마니또 결과 공개하기
    }

    private void resetManito(){
        // TODO : 마니또 발표 전으로 되돌리기. 버그 발생 또는 유사 시 대응을 위함
    }
}
