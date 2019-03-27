import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ShipmentInfo from './shipment-info';
import ShipmentInfoDetail from './shipment-info-detail';
import ShipmentInfoUpdate from './shipment-info-update';
import ShipmentInfoDeleteDialog from './shipment-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ShipmentInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ShipmentInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ShipmentInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ShipmentInfo} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ShipmentInfoDeleteDialog} />
  </>
);

export default Routes;
