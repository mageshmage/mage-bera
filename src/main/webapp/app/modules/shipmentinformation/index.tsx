import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ShipmentInformation from './shipment-information';
import ShipmentInformationDetail from './shipment-information-detail';
import ShipmentInformationUpdate from './shipment-information-update';
import ShipmentInformationDeleteDialog from './shipment-information-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ShipmentInformationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ShipmentInformationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ShipmentInformationDetail} />
      <ErrorBoundaryRoute path={match.url} component={ShipmentInformation} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ShipmentInformationDeleteDialog} />
  </>
);

export default Routes;
