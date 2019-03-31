import { Moment } from 'moment';

export interface IShipmentTracking {
  id?: number;
  trackingDate?: Moment;
  place?: string;
  status?: string;
  shipmentInfoId?: number;
}

export const defaultValue: Readonly<IShipmentTracking> = {};
