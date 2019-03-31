export interface ICarrierDetails {
  id?: number;
  value?: string;
  desc?: string;
  vendorId?: number;
}

export const defaultValue: Readonly<ICarrierDetails> = {};
