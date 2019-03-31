export interface ICarrierDetails {
  id?: number;
  value?: string;
  desc?: string;
  vendorId?: number;
  vendorname?: string;
}

export const defaultValue: Readonly<ICarrierDetails> = {};
