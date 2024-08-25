package com.mdubravac.fonts.dto;

import com.mdubravac.fonts.utils.Font;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NewCollectionDto {
    private String name;
    private List<Font> fonts;
}