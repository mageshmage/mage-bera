export interface IShipmentType {
  id?: number;
  value?: string;
  desc?: string;
  vendorId?: number;
  vendorname?: string;
}

export const defaultValue: Readonly<IShipmentType> = {};
