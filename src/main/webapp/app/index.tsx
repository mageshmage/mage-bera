import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { bindActionCreators } from 'redux';
import { AppContainer } from 'react-hot-loader';

import DevTools from './config/devtools';
import initStore from './config/store';
import { registerLocale } from './config/translation';
import setupAxiosInterceptors from './config/axios-interceptor';
import { clearAuthentication } from './shared/reducers/authentication';
import ErrorBoundary from './shared/error/error-boundary';
import AppComponent from './app';
import { loadIcons } from './config/icon-loader';
import { Switch } from 'react-router-dom';
import { HashRouter as Router } from 'react-router-dom';
import LandingPage from 'app/portal/LandingPage/LandingPage';
import ProfilePage from 'app/portal/ProfilePage/ProfilePage';
import GoDeliverNCRPage from 'app/portal/GoDeliverNCR/HomePage';
import GoDeliverINDPage from 'app/portal/GoDeliverIND/HomePage';
import { Route, Redirect, RouteProps } from 'react-router-dom';
import 'app/assets/scss/material-kit-react.scss?v=1.4.0';

const devTools = process.env.NODE_ENV === 'development' ? <DevTools /> : null;

const store = initStore();
registerLocale(store);

const actions = bindActionCreators({ clearAuthentication }, store.dispatch);
setupAxiosInterceptors(() => actions.clearAuthentication('login.error.unauthorized'));

loadIcons();

const rootEl = document.getElementById('root');

const render = Component =>
  ReactDOM.render(
    <Router>
      <Switch>
        <Route path="/landing-page" component={LandingPage} />
        <Route path="/profile-page" component={ProfilePage} />
        <Route path="/godeliverncr-page" component={GoDeliverNCRPage} />
        <Route path="/godeliverind-page" component={GoDeliverINDPage} />
        <Route path="/" component={() => <Component store={store} devTools={devTools} />} />
      </Switch>
    </Router>,
    rootEl
  );

render(AppComponent);

// This is quite unstable
// if (module.hot) {
//   module.hot.accept('./app', () => {
//     const NextApp = require<{ default: typeof AppComponent }>('./app').default;
//     render(NextApp);
//   });
// }
