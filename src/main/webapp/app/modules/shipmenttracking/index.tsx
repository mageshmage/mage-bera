import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ShipmentTracking from './shipment-tracking';
import ShipmentTrackingDetail from './shipment-tracking-detail';
import ShipmentTrackingUpdate from './shipment-tracking-update';
import ShipmentTrackingDeleteDialog from './shipment-tracking-delete-dialog';
import ShipmentTrackingBulk from './shipment-tracking-bulk';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ShipmentTrackingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/bulk`} component={ShipmentTrackingBulk} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ShipmentTrackingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ShipmentTrackingDetail} />
      <ErrorBoundaryRoute path={match.url} component={ShipmentTracking} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ShipmentTrackingDeleteDialog} />
  </>
);

export default Routes;
