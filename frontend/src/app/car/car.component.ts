import { Component, OnInit } from '@angular/core';
import { Dealership } from '../../model/dealership';
import { Car } from '../../model/car';
import { CarService } from '../_services/car.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarComponent implements OnInit {

  car: Car = {} as Car;
  carId: string = "";

  constructor( private carService: CarService,
    private route: ActivatedRoute) {
      this.carId = this.route.snapshot.paramMap.get('id') !== null ? this.route.snapshot.paramMap.get('id') as string : "";
      carService.getById(this.carId).subscribe((res) => {
        this.car =  res;
      })
    }

  ngOnInit(): void {
  }

  buyCar(car: Car){
      // todo turned off just for testing. Return it after all tests are done
      //this.carService.buyCar(car);
  }
}
