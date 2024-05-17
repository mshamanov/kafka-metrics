package com.mash.kafkametrics.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mash.kafkametrics.databind.JsonTreeToStringDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to represent metrics data.
 *
 * @author Mikhail Shamanov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricsData {
    @JsonProperty(value = "name", required = true)
    @NotEmpty
    private String name;

    @JsonProperty(value = "data", required = true)
    @JsonDeserialize(using = JsonTreeToStringDeserializer.class)
    @NotBlank
    private String data;

    @JsonProperty(value = "description")
    private String description = "";
}
