import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ShipmentType from './shipment-type';
import ShipmentTypeDetail from './shipment-type-detail';
import ShipmentTypeUpdate from './shipment-type-update';
import ShipmentTypeDeleteDialog from './shipment-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ShipmentTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ShipmentTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ShipmentTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ShipmentType} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ShipmentTypeDeleteDialog} />
  </>
);

export default Routes;
