package Repository;

import Domain.Entity.ProjectSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectSettingRepository extends JpaRepository<ProjectSetting, Long> {
}
