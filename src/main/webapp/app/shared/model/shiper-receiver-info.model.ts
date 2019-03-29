import { IShipmentInfo } from 'app/shared/model/shipment-info.model';

export const enum ShiperReceiverType {
  DOCUMENT = 'DOCUMENT',
  PARCEL = 'PARCEL',
  PACKAGE = 'PACKAGE'
}

export interface IShiperReceiverInfo {
  id?: number;
  type?: ShiperReceiverType;
  shipperName?: string;
  phoneNo?: string;
  address?: string;
  city?: string;
  pincode?: string;
  emailId?: string;
  shipmentInfo?: IShipmentInfo;
}

export const defaultValue: Readonly<IShiperReceiverInfo> = {};
