import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PaymentMode from './payment-mode';
import PaymentModeDetail from './payment-mode-detail';
import PaymentModeUpdate from './payment-mode-update';
import PaymentModeDeleteDialog from './payment-mode-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PaymentModeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PaymentModeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PaymentModeDetail} />
      <ErrorBoundaryRoute path={match.url} component={PaymentMode} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PaymentModeDeleteDialog} />
  </>
);

export default Routes;
