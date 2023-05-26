import { Component, OnDestroy, OnInit } from '@angular/core';
import { ElevatorDto } from 'src/app/_model/elevator-dto';
import { InitializeDTO } from 'src/app/_model/initialize-dto';
import { ElevatorsService } from 'src/app/_service/elevators.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy{
  numberOfElevators: number = 0;
  callNumber: number = 3;
  numberChanged: boolean = false;
  elevators: Array<ElevatorDto> = [];
  systemStarted: boolean = false;
  floors: Array<number> = [2, 1, 0, -1, -2];
  intervalId: any;

  constructor (
    private elevatorsService: ElevatorsService,
  ) {}
  
  ngOnInit() {
    this.intervalId = setInterval(() => {
      this.makeStep()
    }, 2000);
  }
  
  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  changeRange(event: any) {
    this.numberOfElevators = event.target.value;
    this.numberChanged = true;
  }

  changeRange2(event: any) {
    this.callNumber = event.target.value;
  }

  isNumberValid(): boolean {
    return (this.numberOfElevators > 16 || this.numberOfElevators < 1) ? false : true;
  }

  isFloorValid(): boolean {
    return (this.callNumber > 2 || this.callNumber < -2) ? false : true;
  }

  start() {
    let initializeDto: InitializeDTO = {
      "numberOfElevators" : this.numberOfElevators 
    }
    this.elevatorsService.initializeElevators(initializeDto).subscribe(result => {
      this.elevators = result;
      this.systemStarted = true;
    })
  }

  callElevator(from: number) {
    this.elevatorsService.callElevator(from, this.callNumber).subscribe(result => {
      if (result === false) {
        alert("All elevators are full, try again later")
      }
    })
  }

  makeStep() {
    if (!this.systemStarted) {
      return;
    }
    this.elevatorsService.makeStep().subscribe(result => {
      this.elevators = result;
    })
  }
  
}
