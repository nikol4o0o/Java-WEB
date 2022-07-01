import { CarPicture } from "./car-picture";

export interface Car {
  id: string,
  modelName: string;
  brand: string;
  transmission: string;
  type: string;
  color: string;
  manufactureYear: Number;
  emissionStandard: Number;
  horsePower: Number;
  truckCapacity: Number;
  price: Number;
  dealership: string;
  carPictures: CarPicture[]
}
