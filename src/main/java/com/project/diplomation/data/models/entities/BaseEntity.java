package com.project.diplomation.data.models.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a utility class for entities, providing the id strategy.
 * <br>(no table representation in database)</br>
 */
// doesn't create a separate table in the database
@MappedSuperclass
@Getter
@ToString
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
