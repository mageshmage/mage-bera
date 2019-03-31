export interface IPaymentMode {
  id?: number;
  value?: string;
  desc?: string;
  vendorId?: number;
}

export const defaultValue: Readonly<IPaymentMode> = {};
