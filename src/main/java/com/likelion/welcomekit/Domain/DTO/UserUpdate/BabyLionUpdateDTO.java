package com.likelion.welcomekit.Domain.DTO.UserUpdate;

import com.likelion.welcomekit.Domain.Types;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BabyLionUpdateDTO {
    private Long id;
    private String name;
    private Long teamId;
    private Types.PartType partType;
    private String department;
}
