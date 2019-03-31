import { Moment } from 'moment';
import { IShipmentTracking } from 'app/shared/model/shipment-tracking.model';
import { IShipmentInfoPOD } from 'app/shared/model/shipment-info-pod.model';
import { IShiperReceiverInfo } from 'app/shared/model/shiper-receiver-info.model';

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
  shipmentTypeId?: number;
  shipmentModeId?: number;
  paymentModeId?: number;
  trackingStatusId?: number;
  vendorId?: number;
  originId?: number;
  destinationId?: number;
  shipmentTrackings?: IShipmentTracking[];
  shipmentInfoPODs?: IShipmentInfoPOD[];
  shipperReceiverInfos?: IShiperReceiverInfo[];
}

export const defaultValue: Readonly<IShipmentInfo> = {
  isThirdParty: false
};
