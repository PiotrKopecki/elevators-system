import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { InitializeDTO } from '../_model/initialize-dto';
import { ElevatorDto } from '../_model/elevator-dto';

@Injectable({
    providedIn: 'root'
})
export class ElevatorsService {
    private backendUrl = environment.backendUrl;

    constructor(private http: HttpClient) { }

    makeStep() {
        let url = `${this.backendUrl}/elevators`;
        return this.http.get<Array<ElevatorDto>>(url);
    }

    initializeElevators(initializeDto: InitializeDTO) {
        let url = `${this.backendUrl}/elevators`;
        return this.http.post<Array<ElevatorDto>>(url, initializeDto);
    }

    callElevator(from: number, to: number) {
        let url = `${this.backendUrl}/elevators`;
        let queryParams = new HttpParams().set('from', Number(from)).set('to', Number(to));
        return this.http.put<boolean>(url, {}, {params: queryParams});
    }
}