import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Vendor from './vendor';
import ShiperReceiverInfo from './shiper-receiver-info';
import ShipmentType from './shipment-type';
import ShipmentMode from './shipment-mode';
import PaymentMode from './payment-mode';
import CarrierDetails from './carrier-details';
import TrackingStatus from './tracking-status';
import ShipmentInfo from './shipment-info';
import ShipmentInfoPOD from './shipment-info-pod';
import City from './city';
import State from './state';
import Country from './country';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/vendor`} component={Vendor} />
      <ErrorBoundaryRoute path={`${match.url}/shiper-receiver-info`} component={ShiperReceiverInfo} />
      <ErrorBoundaryRoute path={`${match.url}/shipment-type`} component={ShipmentType} />
      <ErrorBoundaryRoute path={`${match.url}/shipment-mode`} component={ShipmentMode} />
      <ErrorBoundaryRoute path={`${match.url}/payment-mode`} component={PaymentMode} />
      <ErrorBoundaryRoute path={`${match.url}/carrier-details`} component={CarrierDetails} />
      <ErrorBoundaryRoute path={`${match.url}/tracking-status`} component={TrackingStatus} />
      <ErrorBoundaryRoute path={`${match.url}/shipment-info`} component={ShipmentInfo} />
      <ErrorBoundaryRoute path={`${match.url}/shipment-info-pod`} component={ShipmentInfoPOD} />
      <ErrorBoundaryRoute path={`${match.url}/city`} component={City} />
      <ErrorBoundaryRoute path={`${match.url}/state`} component={State} />
      <ErrorBoundaryRoute path={`${match.url}/country`} component={Country} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
