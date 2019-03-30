import { Moment } from 'moment';
import { ICarrierDetails } from 'app/shared/model/carrier-details.model';
import { IShipmentType } from 'app/shared/model/shipment-type.model';
import { IShipmentMode } from 'app/shared/model/shipment-mode.model';
import { IPaymentMode } from 'app/shared/model/payment-mode.model';
import { ITrackingStatus } from 'app/shared/model/tracking-status.model';
import { IVendor } from 'app/shared/model/vendor.model';
import { IState } from 'app/shared/model/state.model';
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
  carrierDetails?: ICarrierDetails;
  shipmentType?: IShipmentType;
  shipmentMode?: IShipmentMode;
  paymentMode?: IPaymentMode;
  trackingStatus?: ITrackingStatus;
  vendor?: IVendor;
  origin?: IState;
  destination?: IState;
  shipmentTrackings?: IShipmentTracking[];
  shipmentInfoPODs?: IShipmentInfoPOD[];
  shipperReceiverInfos?: IShiperReceiverInfo[];
}

export const defaultValue: Readonly<IShipmentInfo> = {
  isThirdParty: false
};
