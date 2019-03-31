import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';

// prettier-ignore
import vendor, {
  VendorState
} from 'app/entities/vendor/vendor.reducer';
// prettier-ignore
import shiperReceiverInfo, {
  ShiperReceiverInfoState
} from 'app/entities/shiper-receiver-info/shiper-receiver-info.reducer';
// prettier-ignore
import shipmentType, {
  ShipmentTypeState
} from 'app/entities/shipment-type/shipment-type.reducer';
// prettier-ignore
import shipmentMode, {
  ShipmentModeState
} from 'app/entities/shipment-mode/shipment-mode.reducer';
// prettier-ignore
import paymentMode, {
  PaymentModeState
} from 'app/entities/payment-mode/payment-mode.reducer';
// prettier-ignore
import carrierDetails, {
  CarrierDetailsState
} from 'app/entities/carrier-details/carrier-details.reducer';
// prettier-ignore
import trackingStatus, {
  TrackingStatusState
} from 'app/entities/tracking-status/tracking-status.reducer';
// prettier-ignore
import shipmentInfo, {
  ShipmentInfoState
} from 'app/entities/shipment-info/shipment-info.reducer';
// prettier-ignore
import shipmentTracking, {
  ShipmentTrackingState
} from 'app/entities/shipment-tracking/shipment-tracking.reducer';
// prettier-ignore
import shipmentInfoPOD, {
  ShipmentInfoPODState
} from 'app/entities/shipment-info-pod/shipment-info-pod.reducer';
// prettier-ignore
import city, {
  CityState
} from 'app/entities/city/city.reducer';
// prettier-ignore
import state, {
  StateState
} from 'app/entities/state/state.reducer';
// prettier-ignore
import country, {
  CountryState
} from 'app/entities/country/country.reducer';
// prettier-ignore
import shipmentInfomation, {
  ShipmentInformationState
} from 'app/modules/shipmentinformation/shipment-information.reducer';
// prettier-ignore
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly vendor: VendorState;
  readonly shiperReceiverInfo: ShiperReceiverInfoState;
  readonly shipmentType: ShipmentTypeState;
  readonly shipmentMode: ShipmentModeState;
  readonly paymentMode: PaymentModeState;
  readonly carrierDetails: CarrierDetailsState;
  readonly trackingStatus: TrackingStatusState;
  readonly shipmentInfo: ShipmentInfoState;
  readonly shipmentTracking: ShipmentTrackingState;
  readonly shipmentInfoPOD: ShipmentInfoPODState;
  readonly city: CityState;
  readonly state: StateState;
  readonly country: CountryState;
  readonly shipmentInfomation: ShipmentInformationState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  vendor,
  shiperReceiverInfo,
  shipmentType,
  shipmentMode,
  paymentMode,
  carrierDetails,
  trackingStatus,
  shipmentInfo,
  shipmentTracking,
  shipmentInfoPOD,
  city,
  state,
  country,
  shipmentInfomation,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
