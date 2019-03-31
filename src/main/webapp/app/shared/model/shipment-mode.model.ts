export interface IShipmentMode {
  id?: number;
  value?: string;
  desc?: string;
  vendorId?: number;
}

export const defaultValue: Readonly<IShipmentMode> = {};
