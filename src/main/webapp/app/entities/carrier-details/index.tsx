import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CarrierDetails from './carrier-details';
import CarrierDetailsDetail from './carrier-details-detail';
import CarrierDetailsUpdate from './carrier-details-update';
import CarrierDetailsDeleteDialog from './carrier-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CarrierDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CarrierDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CarrierDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={CarrierDetails} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CarrierDetailsDeleteDialog} />
  </>
);

export default Routes;
