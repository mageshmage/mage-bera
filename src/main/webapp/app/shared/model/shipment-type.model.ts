export interface IShipmentType {
  id?: number;
  value?: string;
  desc?: string;
  vendorId?: number;
}

export const defaultValue: Readonly<IShipmentType> = {};
