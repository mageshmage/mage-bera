import { IVendor } from 'app/shared/model/vendor.model';

export interface IPaymentMode {
  id?: number;
  value?: string;
  desc?: string;
  vendor?: IVendor;
}

export const defaultValue: Readonly<IPaymentMode> = {};
