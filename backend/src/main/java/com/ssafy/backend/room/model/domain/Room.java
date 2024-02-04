package com.ssafy.backend.room.model.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Room {
    @Id
    private int roomId;

    public Room() {
    }
}
