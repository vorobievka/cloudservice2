package com.example.cloudservice.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "token_table")
@NoArgsConstructor
public class TokenEntity {

    @jakarta.persistence.Id
    @SequenceGenerator(name = "token_tableSequence", sequenceName = "token_table_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_tableSequence")
    @Column(name = "id")
    private Long Id;

    @Column(name = "token")
    private String token;

    @OneToOne(mappedBy = "token")
    private UserEntity userEntity;

    public TokenEntity(String token, UserEntity userEntity) {
        this.token = token;
        this.userEntity = userEntity;
    }
}
