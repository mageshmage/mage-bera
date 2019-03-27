import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Vendor from './vendor';
import ShiperReceiverInfo from './shiper-receiver-info';
import ShipmentInfo from './shipment-info';
import State from './state';
import Country from './country';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/vendor`} component={Vendor} />
      <ErrorBoundaryRoute path={`${match.url}/shiper-receiver-info`} component={ShiperReceiverInfo} />
      <ErrorBoundaryRoute path={`${match.url}/shipment-info`} component={ShipmentInfo} />
      <ErrorBoundaryRoute path={`${match.url}/state`} component={State} />
      <ErrorBoundaryRoute path={`${match.url}/country`} component={Country} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
