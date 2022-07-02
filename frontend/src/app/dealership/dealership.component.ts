import { Component, OnInit } from '@angular/core';
import { Dealership } from '../../model/dealership';
import { Car } from '../../model/car';
import { DealershipService } from '../_services/dealership.service';
import { CarService } from '../_services/car.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-dealership',
  templateUrl: './dealership.component.html',
  styleUrls: ['./dealership.component.css'],
})
export class DealershipComponent implements OnInit {
  dealership: Dealership;
  cars: Car[] = [];
  dealershipId: string = '';
  loggedUserId = '';

  dealershipsForLoggedUser: String[] = [];
  carForm: boolean = false;
  isNewCar: boolean = false;
  newCar: any = {};
  editCarForm: boolean = false;
  editedCar: any = {};

  constructor(
    private dealershipService: DealershipService,
    private carService: CarService,
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private tokenStorage: TokenStorageService
  ) {
    this.dealershipId =
      this.route.snapshot.paramMap.get('id') !== null
        ? (this.route.snapshot.paramMap.get('id') as string)
        : '';
    this.dealership = {
      id: '1',
      user: 'placeholder',
      name: 'name',
      yearFounded: 2021,
      companyOwner: 'owner',
      cars: [],
    };
    this.loggedUserId = this.tokenStorage.getUser().id;
    dealershipService.getByUserId(this.loggedUserId).subscribe((res) => {
      let ids = [];
      for (let i = 0; i < res.length; i++) {
        ids.push(res[i].dealership);
      }
      this.dealershipsForLoggedUser = ids;
    });
  }

  checkCar(event: Event, car: Car): string {
    this.router.navigate(['/car', { id: car.id }]);
    return (event.target as HTMLInputElement).value;
  }

  ngOnInit(): void {
    this.dealershipService
      .getById(this.dealershipId)
      .subscribe((response: Dealership) => {
        this.dealership = response;
        this.cars = this.dealership.cars;
      });
  }

  showEditCarForm(car: Car) {
    if (!car) {
      this.carForm = false;
      return;
    }
    this.editCarForm = true;
    this.editedCar = car;
    console.log(car);
  }

  showAddCarForm() {
    // resets form if edited car
    if (this.cars.length) {
      this.newCar = {};
    }
    this.carForm = true;
    this.isNewCar = true;
  }

  saveCar(car: Car) {
    if (this.isNewCar) {
      // dealership.user = this.tokenStorage.getUser().id;
      car.dealership = this.dealership.id;
      this.carService.addCar(car).subscribe((res) => {
        console.log('car created successfully!');
        window.location.reload();
      });
    }
    this.carForm = false;
  }

  updateCar() {
    // if(this.tokenStorage.getUser().id !== this.editedCar.user) {
    //   alert("Can't delete this dealership because it's not created by you");
    //   return;
    // }

    //this.editedDealership.user = this.tokenStorage.getUser().id;
    this.carService.updateCar(this.editedCar).subscribe((res) => {
      console.log('car updated successfully!');
      window.location.reload();
    });
    this.editCarForm = false;
    this.editedCar = {};
  }

  removeCar(car: Car) {
    // todo validate that user has authorities to delete the car
    this.carService.deleteCar(car).subscribe((res) => {
      console.log('car deleted successfully!');
      window.location.reload();
    });
  }

  cancelEdits() {
    this.editedCar = {};
    this.editCarForm = false;
  }

  cancelNewCar() {
    this.newCar = {};
    this.carForm = false;
  }
}
