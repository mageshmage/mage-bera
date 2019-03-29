import { Moment } from 'moment';
import { ICarrierDetails } from 'app/shared/model/carrier-details.model';
import { IShipmentType } from 'app/shared/model/shipment-type.model';
import { IShipmentMode } from 'app/shared/model/shipment-mode.model';
import { IPaymentMode } from 'app/shared/model/payment-mode.model';
import { ITrackingStatus } from 'app/shared/model/tracking-status.model';
import { IVendor } from 'app/shared/model/vendor.model';
import { IState } from 'app/shared/model/state.model';
import { IShipmentInfoPOD } from 'app/shared/model/shipment-info-pod.model';
import { IShiperReceiverInfo } from 'app/shared/model/shiper-receiver-info.model';

export interface IShipmentInfo {
  id?: number;
  consignmentNo?: string;
  isThirdParty?: boolean;
  courier?: string;
  carrierRefNo?: string;
  pickupDate?: Moment;
  expectedDeliveryDate?: Moment;
  weight?: number;
  quantity?: number;
  totalFright?: number;
  packageDesciption?: string;
  comments?: string;
  carrierDetails?: ICarrierDetails;
  shipmentType?: IShipmentType;
  shipmentMode?: IShipmentMode;
  paymentMode?: IPaymentMode;
  trackingStatus?: ITrackingStatus;
  vendor?: IVendor;
  origin?: IState;
  destination?: IState;
  shipmentInfoPODS?: IShipmentInfoPOD[];
  shipperReceiverInfos?: IShiperReceiverInfo[];
}

export const defaultValue: Readonly<IShipmentInfo> = {
  isThirdParty: false
};
