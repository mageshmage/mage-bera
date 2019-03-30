import { Moment } from 'moment';
import { IShipmentInfo } from 'app/shared/model/shipment-info.model';

export interface IShipmentTracking {
  id?: number;
  trackingDate?: Moment;
  place?: string;
  status?: string;
  shipmentInfo?: IShipmentInfo;
}

export const defaultValue: Readonly<IShipmentTracking> = {};
