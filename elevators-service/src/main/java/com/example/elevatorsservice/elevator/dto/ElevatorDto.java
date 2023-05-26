package com.example.elevatorsservice.elevator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class ElevatorDto {
	private Integer currentLoad;
	private Integer personsInside;
	private Integer currentFloor;
	private Integer nextFloor;
	private Integer currentDestination;
	private boolean isTaken;
	private List<TransportDto> currentTransports;
}
