import { IVendor } from 'app/shared/model/vendor.model';

export interface IShipmentMode {
  id?: number;
  value?: string;
  desc?: string;
  vendor?: IVendor;
}

export const defaultValue: Readonly<IShipmentMode> = {};
