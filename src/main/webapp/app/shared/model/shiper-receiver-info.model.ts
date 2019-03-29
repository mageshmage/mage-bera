import { ICity } from 'app/shared/model/city.model';
import { IShipmentInfo } from 'app/shared/model/shipment-info.model';

export const enum ShiperReceiverType {
  SHIPPER = 'SHIPPER',
  RECEIVER = 'RECEIVER'
}

export interface IShiperReceiverInfo {
  id?: number;
  type?: ShiperReceiverType;
  shipperName?: string;
  phoneNo?: string;
  address?: string;
  otherCity?: string;
  pincode?: string;
  emailId?: string;
  city?: ICity;
  shipmentInfo?: IShipmentInfo;
}

export const defaultValue: Readonly<IShiperReceiverInfo> = {};
