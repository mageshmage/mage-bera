import { Moment } from 'moment';
import { IShipmentTracking } from 'app/shared/model/shipment-tracking.model';
import { IShipmentInfoPOD } from 'app/shared/model/shipment-info-pod.model';
import { IShiperReceiverInfo, defaultValueShipper, defaultValueReceiver } from 'app/shared/model/shiper-receiver-info.model';

export interface IShipmentInfo {
  id?: number;
  consignmentNo?: string;
  bookingDate?: Moment;
  expectedDeliveryDate?: Moment;
  actualWeight?: number;
  volumetricWeight?: number;
  length?: number;
  width?: number;
  height?: number;
  quantity?: number;
  totalFright?: number;
  packageDesciption?: string;
  isThirdParty?: boolean;
  carrierRefNo?: string;
  deliveredDate?: Moment;
  receivedBy?: string;
  relationShip?: string;
  carrierDetailsId?: number;
  carrierDetailsValue?: string;
  shipmentTypeId?: number;
  shipmentTypeValue?: string;
  shipmentModeId?: number;
  shipmentModeValue?: string;
  paymentModeId?: number;
  paymentModeValue?: string;
  trackingStatusId?: number;
  trackingStatusValue?: string;
  vendorId?: number;
  vendorname?: string;
  originId?: number;
  originValue?: string;
  destinationId?: number;
  destinationValue?: string;
  shipmentTrackings?: IShipmentTracking[];
  shipmentInfoPODs?: IShipmentInfoPOD[];
  shipperReceiverInfos?: IShiperReceiverInfo[];
  shipperInfo?: IShiperReceiverInfo;
  receiverInfo?: IShiperReceiverInfo;
}

export const defaultValue: Readonly<IShipmentInfo> = {
  isThirdParty: false,
  shipperInfo: defaultValueShipper,
  receiverInfo: defaultValueReceiver
};
