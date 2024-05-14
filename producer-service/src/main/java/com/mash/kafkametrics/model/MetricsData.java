package com.mash.kafkametrics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mash.kafkametrics.controller.converter.StringAsJsonRawDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple class to represent metrics data as «key = value» pair.
 *
 * @author Mikhail Shamanov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsData {
    @JsonProperty(value = "name", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String name;

    @JsonProperty(value = "data", required = true)
    @JsonDeserialize(using = StringAsJsonRawDeserializer.class)
    private String data;
}
