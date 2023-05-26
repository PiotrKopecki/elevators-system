package com.example.elevatorsservice.elevator.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransportDto {
	private Integer destinationFloor;
	private Integer from;
	private Integer personWeight;
}
