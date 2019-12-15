import { Moment } from 'moment';
import { IShipmentTracking } from 'app/shared/model/shipment-tracking.model';
import { IShipmentInfoPOD } from 'app/shared/model/shipment-info-pod.model';
import { IShiperReceiverInfo, defaultValueShipper, defaultValueReceiver } from 'app/shared/model/shiper-receiver-info.model';
import { today } from 'app/shared/util/date-utils';

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

export const defaultValue: IShipmentInfo = {
  isThirdParty: false,
  vendorId: 0,
  shipperInfo: defaultValueShipper,
  receiverInfo: defaultValueReceiver
};

export interface IShipmentInfoBulkResponse {
  isError?: boolean;
  errorList?: String[];
}
export const defaultValueBulk: IShipmentInfoBulkResponse = {
  isError: false,
  errorList: []
};

export interface IShipmentInformationSearchDTO {
  consignmentNo?: string;
  bookingDateFrom?: Moment;
  bookingDateTo?: Moment;
  expectedDeliveryDateFrom?: Moment;
  expectedDeliveryDateTo?: Moment;
  carrierRefNo?: string;
  deliveredDateFrom?: Moment;
  deliveredDateTo?: Moment;
  carrierDetailsId?: number;
  shipmentTypeId?: number;
  shipmentModeId?: number;
  paymentModeId?: number;
  trackingStatusId?: number;
  vendorId?: number;
  originId?: number;
  destinationId?: number;
}

export const defaultValueShipmentInformationSearchDTO: IShipmentInformationSearchDTO = {
  bookingDateFrom: today()
};
