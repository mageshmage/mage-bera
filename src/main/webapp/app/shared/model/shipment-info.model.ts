import { Moment } from 'moment';
import { IVendor } from 'app/shared/model/vendor.model';
import { IState } from 'app/shared/model/state.model';
import { IShipmentInfoPOD } from 'app/shared/model/shipment-info-pod.model';
import { IShiperReceiverInfo } from 'app/shared/model/shiper-receiver-info.model';

export const enum CARRIER {
  DHL = 'DHL',
  FEDEX = 'FEDEX'
}

export const enum ShipmentType {
  DOCUMENT = 'DOCUMENT',
  PARCEL = 'PARCEL',
  PACKAGE = 'PACKAGE'
}

export const enum ShipmentMode {
  AIR = 'AIR',
  ROAD = 'ROAD',
  SEA = 'SEA'
}

export const enum PaymentMode {
  TOPAY = 'TOPAY',
  PAID = 'PAID',
  CREDIT = 'CREDIT',
  CASH = 'CASH',
  CHEQUE = 'CHEQUE',
  ACCOUNT = 'ACCOUNT',
  UPI = 'UPI'
}

export const enum Status {
  INTRANSIENT = 'INTRANSIENT',
  PENDING = 'PENDING',
  DELIVERED = 'DELIVERED',
  DELAYED = 'DELAYED',
  ONHOLD = 'ONHOLD'
}

export interface IShipmentInfo {
  id?: number;
  consignmentNo?: string;
  isThirdParty?: boolean;
  carrier?: CARRIER;
  courier?: string;
  carrierRefNo?: string;
  shipmentType?: ShipmentType;
  shipmentMode?: ShipmentMode;
  pickupDate?: Moment;
  expectedDeliveryDate?: Moment;
  weight?: number;
  quantity?: number;
  totalFright?: number;
  packageDesciption?: string;
  paymentMode?: PaymentMode;
  status?: Status;
  comments?: string;
  vendor?: IVendor;
  origin?: IState;
  destination?: IState;
  shipmentInfoPODS?: IShipmentInfoPOD[];
  shipperReceiverInfos?: IShiperReceiverInfo[];
}

export const defaultValue: Readonly<IShipmentInfo> = {
  isThirdParty: false
};
