import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ShiperReceiverInfo from './shiper-receiver-info';
import ShiperReceiverInfoDetail from './shiper-receiver-info-detail';
import ShiperReceiverInfoUpdate from './shiper-receiver-info-update';
import ShiperReceiverInfoDeleteDialog from './shiper-receiver-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ShiperReceiverInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ShiperReceiverInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ShiperReceiverInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={ShiperReceiverInfo} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ShiperReceiverInfoDeleteDialog} />
  </>
);

export default Routes;
