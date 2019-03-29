import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ShipmentMode from './shipment-mode';
import ShipmentModeDetail from './shipment-mode-detail';
import ShipmentModeUpdate from './shipment-mode-update';
import ShipmentModeDeleteDialog from './shipment-mode-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ShipmentModeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ShipmentModeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ShipmentModeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ShipmentMode} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ShipmentModeDeleteDialog} />
  </>
);

export default Routes;
