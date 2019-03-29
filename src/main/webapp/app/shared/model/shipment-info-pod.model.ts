import { IShipmentInfo } from 'app/shared/model/shipment-info.model';

export interface IShipmentInfoPOD {
  id?: number;
  podContentType?: string;
  pod?: any;
  comments?: string;
  shipmentInfo?: IShipmentInfo;
}

export const defaultValue: Readonly<IShipmentInfoPOD> = {};
