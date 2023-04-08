package com.elinxer.cloud.library.server.assist.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@Component
@RefreshScope
@Data
public class AssistCnfValues {

    @Value(value = "${assistapp.geo-distance:1000}")
    private double geoDistance;


}
