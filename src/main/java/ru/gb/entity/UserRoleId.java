package ru.gb.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserRoleId {

    private Long userId;
    private Long roleId;

}
