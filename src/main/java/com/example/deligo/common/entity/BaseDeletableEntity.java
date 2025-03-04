package com.example.deligo.common.entity;
<<<<<<< HEAD

import jakarta.persistence.Column;
=======
>>>>>>> feature/store
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseDeletableEntity extends BaseTimeEntity {

    @Column
    private LocalDateTime deletedAt = null;

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}