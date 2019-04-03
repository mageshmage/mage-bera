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
  shipmentInfoId?: number;
}

export const defaultValue: Readonly<IShiperReceiverInfo> = {};

export const defaultValueShipper: Readonly<IShiperReceiverInfo> = {
  type: ShiperReceiverType.CONSIGNOR
};

export const defaultValueReceiver: Readonly<IShiperReceiverInfo> = {
  type: ShiperReceiverType.CONSIGNOR
};
