import { IVendor } from 'app/shared/model/vendor.model';

export interface IShipmentType {
  id?: number;
  value?: string;
  desc?: string;
  vendor?: IVendor;
}

export const defaultValue: Readonly<IShipmentType> = {};
