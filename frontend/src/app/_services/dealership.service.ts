import { Injectable } from '@angular/core';
import { Dealership } from '../../model/dealership';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable } from 'rxjs';

const API_URL = 'http://localhost:8083/api/dealerships/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class DealershipService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get<Dealership>(API_URL);
  }

  getById(dealershipId: string | null): Observable<any> {
    return this.http.get<Dealership>(API_URL + dealershipId);
  }

  addDealership(dealership: Dealership): Observable<any> {
    return this.http.post(API_URL, dealership, httpOptions);
  }

  updateDealership(dealership: Dealership): Observable<any> {
    return this.http.put(API_URL, dealership, httpOptions);
  }

  deleteDealership(dealership: Dealership): Observable<any> {
    return this.http.delete(API_URL + dealership.id);
  }

  getByUserId(userId: string): Observable<any> {
    return this.http.get<Dealership>(API_URL + "user/" +  userId);
  }
}
