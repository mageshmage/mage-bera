import { IVendor } from 'app/shared/model/vendor.model';

export interface ICarrierDetails {
  id?: number;
  value?: string;
  desc?: string;
  vendor?: IVendor;
}

export const defaultValue: Readonly<ICarrierDetails> = {};
