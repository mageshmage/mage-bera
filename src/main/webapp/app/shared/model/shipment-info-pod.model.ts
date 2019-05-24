export interface IShipmentInfoPOD {
  id?: number;
  podContentType?: string;
  pod?: any;
  comments?: string;
  shipmentInfoId?: number;
  vendorId?: number;
  searchValue?: string;
}

export const defaultValue: Readonly<IShipmentInfoPOD> = {};
