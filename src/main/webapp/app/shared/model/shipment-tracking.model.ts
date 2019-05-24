import { Moment } from 'moment';

export interface IShipmentTracking {
  id?: number;
  trackingDate?: Moment;
  place?: string;
  status?: string;
  shipmentInfoId?: number;
  isInTransit?: boolean;
  isReachedNearestHub?: boolean;
  isOutForDelivery?: boolean;
  isDelivered?: boolean;
  receivedBy?: string;
  relationShip?: string;
}

export const defaultValue: Readonly<IShipmentTracking> = {};
