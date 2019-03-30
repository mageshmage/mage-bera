import { IShipmentInfo } from 'app/shared/model/shipment-info.model';

export const enum ShiperReceiverType {
  CONSIGNOR = 'CONSIGNOR',
  CONSIGNEE = 'CONSIGNEE'
}

export interface IShiperReceiverInfo {
  id?: number;
  type?: ShiperReceiverType;
  name?: string;
  phoneNo?: string;
  address?: string;
  city?: string;
  pincode?: string;
  emailId?: string;
  shipmentInfo?: IShipmentInfo;
}

export const defaultValue: Readonly<IShiperReceiverInfo> = {};
