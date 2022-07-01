import { Injectable } from '@angular/core';
import { Dealership } from '../../model/dealership';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Car } from '../../model/car';

const API_URL = 'http://localhost:8083/api/cars/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class CarService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get<Car>(API_URL);
  }

  getById(carId: string): Observable<any> {
    return this.http.get<Car>(API_URL + carId);
  }

  addCar(car: Car): Observable<any> {
    return this.http.post(API_URL, car, httpOptions);
  }

  updateCar(car: Car): Observable<any> {
    return this.http.put(API_URL, car, httpOptions);
  }

  deleteCar(car: Car): Observable<any> {
    return this.http.delete(API_URL + car.id);
  }

  buyCar(car: Car): Observable<any> {
    return this.http.post(API_URL + car.id + "/buy", httpOptions);
  }
}
