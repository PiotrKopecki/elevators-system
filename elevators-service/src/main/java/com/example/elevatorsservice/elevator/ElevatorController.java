package com.example.elevatorsservice.elevator;

import com.example.elevatorsservice.elevator.dto.ElevatorDto;
import com.example.elevatorsservice.elevator.dto.InitializeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url}/elevators")
public class ElevatorController {
	private final ElevatorService elevatorService;

	@PostMapping
	public ResponseEntity<ArrayList<ElevatorDto>> initializeElevators(@RequestBody InitializeDto initializeDto) {
		if (initializeDto == null) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		ArrayList<ElevatorDto> elevators = elevatorService.initializeElevators(initializeDto.getNumberOfElevators());
		return new ResponseEntity<>(elevators, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<ArrayList<ElevatorDto>> makeStep() {
		elevatorService.makeStep();
		ArrayList<ElevatorDto> elevatorDtos = elevatorService.getStatus();
		return new ResponseEntity<>(elevatorDtos, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Boolean> callElevator(@RequestParam Integer from, @RequestParam Integer to) {
		boolean status = elevatorService.callElevator(from, to);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}
}
