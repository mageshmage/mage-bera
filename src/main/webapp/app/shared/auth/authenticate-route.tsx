import React from 'react';
import { connect } from 'react-redux';
import { Route, Redirect, RouteProps } from 'react-router-dom';
import { Translate } from 'app/shared/components/language';
import { IRootState } from 'app/shared/reducers';
import ErrorBoundary from 'app/shared/error/error-boundary';

interface IOwnProps extends RouteProps {}

export interface IAuthenticateRouteProps extends IOwnProps, StateProps {}

export const AuthenticateRouteComponent = ({
  component: Component,
  isAuthenticated,
  sessionHasBeenFetched,
  ...rest
}: IAuthenticateRouteProps) => {
  const renderRedirect = props => {
    if (!sessionHasBeenFetched) {
      return <div />;
    } else {
      return isAuthenticated ? (
        <ErrorBoundary>
          <Component {...props} />
        </ErrorBoundary>
      ) : (
        <Redirect
          to={{
            pathname: '/login',
            search: props.location.search,
            state: { from: props.location }
          }}
        />
      );
    }
  };

  if (!Component) throw new Error(`A component needs to be specified for private route for path ${(rest as any).path}`);

  return <Route {...rest} render={renderRedirect} />;
};

export const hasAnyAuthority = (authorities: string[], hasAnyAuthorities: string[]) => {
  if (authorities && authorities.length !== 0) {
    if (hasAnyAuthorities.length === 0) {
      return true;
    }
    return hasAnyAuthorities.some(auth => authorities.includes(auth));
  }
  return false;
};

const mapStateToProps = ({ authentication: { isAuthenticated, sessionHasBeenFetched } }: IRootState, {  }: IOwnProps) => ({
  isAuthenticated,
  sessionHasBeenFetched
});

type StateProps = ReturnType<typeof mapStateToProps>;

/**
 * A route wrapped in an authentication check so that routing happens only when you are authenticated.
 * Accepts same props as React router Route.
 */
export const AuthenticateRoute = connect<StateProps, undefined, IOwnProps>(
  mapStateToProps,
  null,
  null,
  { pure: false }
)(AuthenticateRouteComponent);

export default AuthenticateRoute;
