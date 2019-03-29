import { Moment } from 'moment';
import { IShipmentInfo } from 'app/shared/model/shipment-info.model';

export interface IShipmentTracking {
  id?: number;
  trackingDate?: Moment;
  activities?: string;
  shipmentInfo?: IShipmentInfo;
}

export const defaultValue: Readonly<IShipmentTracking> = {};
