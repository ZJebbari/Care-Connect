package com.zj.careconnect.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Component
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(
            cascade = CascadeType.ALL, // Includes REMOVE
            orphanRemoval = true,      // Ensures removal of orphaned User entities
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String gender;
    private String address;

    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;

    @JsonIgnore
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @PrePersist
    public void setCreateTime() {
        this.createTime = LocalDate.now();
    }
}

