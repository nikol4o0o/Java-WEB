import { Car } from "./car";

export interface Dealership {
    id: string;
    user: string;
    name: string;
    yearFounded: Number;
    companyOwner: string;
    cars: Car[];
}
