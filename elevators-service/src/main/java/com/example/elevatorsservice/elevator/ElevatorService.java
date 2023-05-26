package com.example.elevatorsservice.elevator;

import com.example.elevatorsservice.elevator.dto.ElevatorDto;
import com.example.elevatorsservice.elevator.dto.TransportDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ElevatorService {
	private ArrayList<ElevatorDto> elavators = new ArrayList<>();

	public ArrayList<ElevatorDto> initializeElevators(Integer numberOfElevators) {
		this.elavators = new ArrayList<>();
		for (int i = 0; i < numberOfElevators; i++) {
			this.elavators.add(new ElevatorDto(0, 0, 0, 0, 0, false, new ArrayList<>()));
		}
		return this.elavators;
	}

	private ElevatorDto findClosestElevator(Integer from, List<ElevatorDto> elevators) {
		int distance = Integer.MAX_VALUE;
		ElevatorDto closestElevator = null;

		for (ElevatorDto elevator : elevators) {
			if (Math.abs(elevator.getCurrentFloor() - from) == 0) { //same floor
				return elevator;
			} else if (Math.abs(elevator.getCurrentFloor() - from) < distance) {
				closestElevator = elevator;
				distance = Math.abs(elevator.getCurrentFloor() - from);
			}
		}
		return closestElevator;
	}

	private ElevatorDto chooseElevator(Integer from, Integer personWeight) {
		List<ElevatorDto> notFullElevators = this.elavators
				.stream()
				.filter(elevatorDto -> elevatorDto.getCurrentLoad() + personWeight <= ElevatorConstants.MAXIMUM_CAPACITY &&
						elevatorDto.getPersonsInside() + 1 <= ElevatorConstants.MAX_PERSONS_INSIDE)
				.toList();
		if (notFullElevators.isEmpty()) {
			return null;
		}
		List<ElevatorDto> unusedElevators = notFullElevators
				.stream()
				.filter(elevatorDto -> elevatorDto.getCurrentTransports().isEmpty())
				.toList();

		if (!unusedElevators.isEmpty()) {
			return findClosestElevator(from, unusedElevators);
		}
		return findClosestElevator(from, notFullElevators);
	}

	public boolean callElevator(Integer from, Integer to) {
		int personWeight = (int)(Math.random() * (100 - 50) + 50); // 50-100 kg weight
		ElevatorDto elevatorDto = chooseElevator(from, personWeight);
		if (elevatorDto == null) {
			log.info("All elevators are full!");
			return false;
		}
		TransportDto transportDto = TransportDto.builder()
				.destinationFloor(to)
				.from(from)
				.personWeight(personWeight)
				.build();

		int index = this.elavators.indexOf(elevatorDto);
		if (!this.elavators.get(index).isTaken()) {
			this.elavators.get(index).setTaken(true);
			if (!Objects.equals(this.elavators.get(index).getCurrentFloor(), from)) {
				this.elavators.get(index).setCurrentDestination(from);
			} else {
				this.elavators.get(index).setCurrentDestination(to);
				personGetsIn(this.elavators.get(index), personWeight);
			}
		}
		this.elavators.get(index).getCurrentTransports().add(transportDto);
		log.info(MessageFormat.format("Found elevator: {0} to go from {1} to {2}", index, from, to));
		return true;
	}

	public ArrayList<ElevatorDto> getStatus() {
		return this.elavators;
	}

	private void personGetsIn(ElevatorDto elevatorDto, Integer personWeight) {
		elevatorDto.setPersonsInside(elevatorDto.getPersonsInside() + 1);
		elevatorDto.setCurrentLoad(elevatorDto.getCurrentLoad() + personWeight);
	}

	private void personGetsOut(ElevatorDto elevatorDto, Integer personWeight) {
		elevatorDto.setPersonsInside(elevatorDto.getPersonsInside() - 1);
		elevatorDto.setCurrentLoad(elevatorDto.getCurrentLoad() - personWeight);
	}

	public void makeStep() {
		this.elavators.forEach(elevatorDto -> {
			if (!elevatorDto.isTaken()) {
				return;
			}
			int currentFloor = elevatorDto.getCurrentFloor();
			if (Objects.equals(currentFloor, elevatorDto.getCurrentDestination())) { 			//elevator arrived at destination

				TransportDto currentTransport = elevatorDto.getCurrentTransports().get(0);
				if (Objects.equals(currentFloor, currentTransport.getFrom())) { 				//arrived for person
					personGetsIn(elevatorDto, currentTransport.getPersonWeight());
					elevatorDto.setCurrentDestination(currentTransport.getDestinationFloor());
				}
				else {																			//arrived with person
					for (int i = 0; i < elevatorDto.getCurrentTransports().size(); i++) {
						if (elevatorDto.getCurrentTransports().get(i).getDestinationFloor().equals(currentFloor)) {
							personGetsOut(elevatorDto, elevatorDto.getCurrentTransports().get(0).getPersonWeight());
							elevatorDto.getCurrentTransports().remove(i);
						}
					}
					if (elevatorDto.getCurrentTransports().isEmpty()) {
						elevatorDto.setTaken(false);
					} else {
						TransportDto next = elevatorDto.getCurrentTransports().get(0);
						elevatorDto.setCurrentDestination(next.getFrom());
					}
				}
				elevatorDto.setNextFloor(elevatorDto.getCurrentFloor());
			}
			int direction = currentFloor > elevatorDto.getCurrentDestination() ? -1 : 1;
			elevatorDto.setCurrentFloor(elevatorDto.getNextFloor());
			elevatorDto.setNextFloor(elevatorDto.getNextFloor() + direction);
		});
	}
}
