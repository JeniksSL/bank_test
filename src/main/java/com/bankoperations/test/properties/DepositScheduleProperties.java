package com.bankoperations.test.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;


@Getter
@Setter
@RequiredArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "deposit")
public class DepositScheduleProperties {

	private BigDecimal baseModifier;
	private BigDecimal maxModifier;
}
