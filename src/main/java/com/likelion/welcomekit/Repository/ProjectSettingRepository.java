package com.likelion.welcomekit.Repository;

import com.likelion.welcomekit.Domain.Entity.ProjectSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectSettingRepository extends JpaRepository<ProjectSetting, Long> {
}
