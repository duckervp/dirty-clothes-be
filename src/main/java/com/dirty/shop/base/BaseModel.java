package com.dirty.shop.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel extends Auditable {
    public abstract Long getId();

    @Column(name = "is_deleted")
    private boolean deleted;
}
