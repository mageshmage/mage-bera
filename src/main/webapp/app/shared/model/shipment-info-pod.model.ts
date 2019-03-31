export interface IShipmentInfoPOD {
  id?: number;
  podContentType?: string;
  pod?: any;
  comments?: string;
  shipmentInfoId?: number;
}

export const defaultValue: Readonly<IShipmentInfoPOD> = {};
