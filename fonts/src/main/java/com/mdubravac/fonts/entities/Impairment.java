package com.mdubravac.fonts.entities;

import com.mdubravac.fonts.enums.ImpairmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "impairment")
public class Impairment {
    @Id
    @Enumerated(EnumType.STRING)
    private ImpairmentType impairmentType;
}