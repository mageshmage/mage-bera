import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ShipmentInfoPOD from './shipment-info-pod';
import ShipmentInfoPODDetail from './shipment-info-pod-detail';
import ShipmentInfoPODUpdate from './shipment-info-pod-update';
import ShipmentInfoPODDeleteDialog from './shipment-info-pod-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ShipmentInfoPODUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ShipmentInfoPODUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ShipmentInfoPODDetail} />
      <ErrorBoundaryRoute path={match.url} component={ShipmentInfoPOD} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ShipmentInfoPODDeleteDialog} />
  </>
);

export default Routes;
