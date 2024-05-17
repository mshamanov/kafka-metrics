package com.mash.kafkametrics.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mash.kafkametrics.databind.StringToJsonTreeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Class to represent metrics data.
 *
 * @author Mikhail Shamanov
 */
@Entity
@Table(name = "metrics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetricsData {
    @Id
    @Column(name = "name", nullable = false)
    @JsonProperty(value = "name", required = true)
    private String name;

    @Column(name = "data", nullable = false, columnDefinition = "text")
    @JsonProperty(value = "data", required = true)
    @JsonSerialize(using = StringToJsonTreeSerializer.class)
    private String data;

    @Column(name = "description")
    @JsonProperty(value = "description")
    private String description;
}
