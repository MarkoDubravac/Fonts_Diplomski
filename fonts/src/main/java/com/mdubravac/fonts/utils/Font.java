package com.mdubravac.fonts.utils;

import com.mdubravac.fonts.enums.TypeFaces;
import jakarta.persistence.Embeddable;

@Embeddable
public record Font(String name, TypeFaces family) {
}
