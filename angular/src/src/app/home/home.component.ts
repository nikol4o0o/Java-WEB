import { Component, OnInit } from '@angular/core';
import { Dealership } from '../../model/dealership';
import { DealershipService } from '../_services/dealership.service';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { TokenStorageService } from '../_services/token-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  dealerships: Dealership[] = [];

  dealershipForm: boolean = false;
  isNewDealership: boolean = false;
  newDealership: any = {};
  editDealershipForm: boolean = false;
  editedDealership: any = {};

  constructor(
    private dealershipService: DealershipService,
    private router: Router,
    private tokenStorage: TokenStorageService
  ) {}

  checkDealershipCars(event: Event, dealership: Dealership): string {
    this.router.navigate(['/dealership', { id: dealership.id }]);
    return (event.target as HTMLInputElement).value;
  }

  ngOnInit(): void {
    this.dealershipService.getAll().subscribe((response: Dealership[]) => {
      this.dealerships = response;
    });
  }

  showEditDealershipForm(dealership: Dealership) {
    if (!dealership) {
      this.dealershipForm = false;
      return;
    }
    this.editDealershipForm = true;
    this.editedDealership = dealership;
  }

  showAddDealershipForm() {
    // resets form if edited user
    if (this.dealerships.length) {
      this.newDealership = {};
    }
    this.dealershipForm = true;
    this.isNewDealership = true;
  }

  saveDealership(dealership: Dealership) {
    if (this.isNewDealership) {
      // add a new dealership
      dealership.user = this.tokenStorage.getUser().id;
      this.dealershipService.addDealership(dealership).subscribe((res) => {
        console.log('dealership created successfully!');
        window.location.reload();
      });
    }
    this.dealershipForm = false;
  }

  updateDealership() {
    if(this.tokenStorage.getUser().id !== this.editedDealership.user) {
      alert("Can't delete this dealership because it's not created by you");
      return;
    }

    //this.editedDealership.user = this.tokenStorage.getUser().id;
    this.dealershipService
      .updateDealership(this.editedDealership)
      .subscribe((res) => {
        console.log('dealership updated successfully!');
        window.location.reload();
      });
    this.editDealershipForm = false;
    this.editedDealership = {};
  }

  removeDealership(dealership: Dealership) {
    if(this.tokenStorage.getUser().id !== dealership.user) {
      alert("Can't delete this dealership because it's not created by you");
      return;
    }

    this.dealershipService.deleteDealership(dealership).subscribe(res => {
      console.log('dealership deleted successfully!');
      window.location.reload();
 });;
  }

  cancelEdits() {
    this.editedDealership = {};
    this.editDealershipForm = false;
  }

  cancelNewDealership() {
    this.newDealership = {};
    this.dealershipForm = false;
  }
}
