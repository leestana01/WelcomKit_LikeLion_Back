package Domain.Entity;


import Domain.PartType;

public interface User {
    String getName();
    String getPassword();

    String getDepartment();
    PartType getPart();
    boolean isManager();

    Long getTeamId();
    User getManitoTo();
    User getManitoFrom();
}
