package com.zj.careconnect.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zj.careconnect.enums.ProcedureType;
import com.zj.careconnect.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Getter
@Component
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "appointments") // Table name in the database
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcedureType procedureType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date; // Represents the date of the appointment

    @JsonFormat(pattern = "HH:mm:ss")
    @Column(nullable = false)
    private LocalTime time; // Represents the time of the appointment

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdAt; // Represents when the record was created

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt; // Represents when the record was last updated

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="patient_id" ,referencedColumnName = "id")
    private Patient patient;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="doctor_id" ,referencedColumnName = "id")
    private Doctor doctor;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now(); // Set createdAt before the entity is persisted
    }

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now(); // Set updatedAt before the entity is updated
    }
}
