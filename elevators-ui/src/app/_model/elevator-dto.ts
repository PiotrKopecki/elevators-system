import { TransportDto } from "./transport-dto";

export interface ElevatorDto {
    currentLoad: number,
    personsInside: number,
    currentFloor: number,
    nextFloor: number,
    currentDestination: number,
    isTaken: boolean,
    currentTransports: Array<TransportDto>
}