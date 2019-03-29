import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TrackingStatus from './tracking-status';
import TrackingStatusDetail from './tracking-status-detail';
import TrackingStatusUpdate from './tracking-status-update';
import TrackingStatusDeleteDialog from './tracking-status-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TrackingStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TrackingStatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TrackingStatusDetail} />
      <ErrorBoundaryRoute path={match.url} component={TrackingStatus} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TrackingStatusDeleteDialog} />
  </>
);

export default Routes;
