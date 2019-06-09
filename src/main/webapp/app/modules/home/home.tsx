import './home.css';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate, translate, TextFormat, openFile, byteSize } from 'react-jhipster';
import { connect } from 'react-redux';
import { Row, Col, Alert, Button, InputGroup, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { getSearchWithConsignmentNo, reset } from 'app/modules/shipmentinformation/shipment-information.reducer';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { APP_DATEONLY_FORMAT, APP_TIMEONLY_FORMAT, APP_TIMESTAMP_FORMAT } from 'app/config/constants';

export interface IHomeProp extends StateProps, DispatchProps {
  vendorId: number;
}

export interface IHomeState {
  search: string;
}
export class Home extends React.Component<IHomeProp, IHomeState> {
  state: IHomeState = {
    search: ''
  };

  componentDidMount() {
    this.props.getSession();
    this.props.reset();
  }

  search = () => {
    //alert('search - ' + this.state.search + ' VendorId - ' + this.props.vendorId)

    let vendorId: number = 0;
    if (this.props.vendorId != undefined) vendorId = this.props.vendorId;
    if (this.state.search) {
      this.props.getSearchWithConsignmentNo(this.state.search, vendorId);
    }
  };

  handleSearch = event => this.setState({ search: event.target.value });

  clear = () => {
    this.setState({ search: '' }, () => {
      //this.props.getEntities();
      this.props.reset();
    });
  };

  render() {
    const { account, shipmentInfo } = this.props;
    //console.log(shipmentInfo);
    return (
      <Row>
        {/*<Col md="9">
          <h2>
            <Translate contentKey="home.title">Welcome, to Cargo Tracker!</Translate>
          </h2>
          {account && account.login ? (
            <div>
              <Alert color="success">
                <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                  You are logged in as user {account.login}.
                </Translate>
              </Alert>
            </div>
          ) : (
            <div>
              <Alert color="warning">
                <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>
                <Link to="/login" className="alert-link">
                  <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
                </Link>
              </Alert>

              <Alert color="warning">
                <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>
                &nbsp;
                <Link to="/register" className="alert-link">
                  <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
                </Link>
              </Alert>
            </div>
          )}
        </Col>
        <Col md="3" className="pad">
          <span className="hipster rounded" />
        </Col>*/}
        <Col md="12">
          <Row>
            <Col sm="12" md={{ size: 4, offset: 4 }}>
              <AvForm onSubmit={this.search}>
                <AvGroup>
                  <InputGroup>
                    <AvInput
                      type="text"
                      name="search"
                      value={this.state.search}
                      onChange={this.handleSearch}
                      placeholder={translate('cargotrackerApp.shipmentTracking.public.trackingId')}
                    />
                    &nbsp;
                    <Button className="input-group-addon">
                      <FontAwesomeIcon icon="search" />
                    </Button>
                    &nbsp;
                    <Button type="reset" className="input-group-addon" onClick={this.clear}>
                      <FontAwesomeIcon icon="trash" />
                    </Button>
                  </InputGroup>
                </AvGroup>
              </AvForm>
            </Col>
          </Row>
        </Col>
        {shipmentInfo &&
          shipmentInfo.shipmentTrackings &&
          shipmentInfo.shipmentTrackings.length > 0 && (
            <Col md="12">
              <Row>
                <Col sm="12" md={{ size: 8, offset: 2 }}>
                  <h4>Tracking ID: {this.state.search}</h4>
                  <div className="table-responsive">
                    <Table responsive hover bordered>
                      <thead>
                        <tr>
                          <th className="hand">
                            <Translate contentKey="cargotrackerApp.shipmentTracking.public.currentStatus">Current Status</Translate>{' '}
                          </th>
                          <th className="hand">
                            <Translate contentKey="cargotrackerApp.shipmentTracking.public.deliveredOn">Delivered On</Translate>{' '}
                          </th>
                          <th className="hand">
                            <Translate contentKey="cargotrackerApp.shipmentTracking.public.receivedBy">Received By</Translate>
                          </th>
                          <th className="hand">
                            <Translate contentKey="cargotrackerApp.shipmentTracking.public.relationShip">Relationship</Translate>
                          </th>
                          {shipmentInfo.shipmentInfoPODs.length > 0 &&
                            shipmentInfo.shipmentInfoPODs[0].pod && (
                              <th>
                                <Translate contentKey="cargotrackerApp.shipmentInfoPOD.pod">Pod</Translate>
                              </th>
                            )}
                        </tr>
                      </thead>
                      <tbody>
                        <tr key={`entity`}>
                          <td>{shipmentInfo.isDelivered ? 'Delivered' : 'In Transit'}</td>
                          <td>
                            {shipmentInfo.deliveredDate ? (
                              <TextFormat type="date" value={shipmentInfo.deliveredDate} format={APP_TIMESTAMP_FORMAT} />
                            ) : (
                              '-'
                            )}
                          </td>
                          <td>{shipmentInfo.receivedBy ? shipmentInfo.receivedBy : '-'}</td>
                          <td>{shipmentInfo.relationShip ? shipmentInfo.relationShip : '-'}</td>
                          {shipmentInfo.shipmentInfoPODs.length > 0 &&
                            shipmentInfo.shipmentInfoPODs[0].pod && (
                              <td>
                                <div>
                                  <a
                                    onClick={openFile(
                                      shipmentInfo.shipmentInfoPODs[0].podContentType,
                                      shipmentInfo.shipmentInfoPODs[0].pod
                                    )}
                                  >
                                    <Translate contentKey="entity.action.open">Open</Translate>
                                    &nbsp;
                                  </a>
                                  {/*<span>
                                {shipmentInfo.shipmentInfoPODs[0].podContentType}, {byteSize(shipmentInfo.shipmentInfoPODs[0].pod)}
                              </span>*/}
                                </div>
                              </td>
                            )}
                        </tr>
                      </tbody>
                    </Table>
                  </div>
                </Col>
              </Row>

              <Row>
                <Col sm="12" md={{ size: 8, offset: 2 }}>
                  <div className="container">
                    <div className="row bs-wizard col-xs-offset-1 col-sm-offset-1 col-md-offset-1 col-lg-offset-1" id="track_bar">
                      <div className="col-xs-2 bs-wizard-step complete">
                        <div className="text-center bs-wizard-stepnum">{shipmentInfo.shipperInfo.city}</div>

                        <div className="progress">
                          <div className="progress-bar" />
                        </div>

                        <div className="bs-wizard-dot" />

                        <div className="bs-wizard-info text-center"> Shipment Details Received</div>
                      </div>

                      <div
                        className={
                          'col-xs-2 bs-wizard-step ' + (!shipmentInfo.isInTransit && !shipmentInfo.isDelivered ? 'active' : 'complete')
                        }
                      >
                        <div className="text-center bs-wizard-stepnum">.</div>

                        <div className="progress">{shipmentInfo.isInTransit && <div className="progress-bar" />}</div>

                        <div className="bs-wizard-info text-center"> In Transit</div>
                      </div>
                      <div
                        className={
                          'col-xs-2 bs-wizard-step ' +
                          (!shipmentInfo.isReachedNearestHub && !shipmentInfo.isDelivered ? 'active' : 'complete')
                        }
                      >
                        <div className="text-center bs-wizard-stepnum">.</div>

                        <div className="progress">{shipmentInfo.isReachedNearestHub && <div className="progress-bar" />}</div>

                        <div className="bs-wizard-dot" />

                        <div className="bs-wizard-info text-center"> Arrived at your Nearest Hub</div>
                      </div>

                      <div
                        className={
                          'col-xs-2 bs-wizard-step ' + (!shipmentInfo.isOutForDelivery && !shipmentInfo.isDelivered ? 'active' : 'complete')
                        }
                      >
                        <div className="text-center bs-wizard-stepnum">.</div>
                        <div className="progress">{shipmentInfo.isOutForDelivery && <div className="progress-bar" />}</div>

                        <div className="bs-wizard-dot" />

                        <div className="bs-wizard-info text-center"> Out for Delivery</div>
                      </div>

                      <div className={'col-xs-2 bs-wizard-step ' + (shipmentInfo.isDelivered ? 'active' : '')}>
                        <div className="text-center bs-wizard-stepnum">{shipmentInfo.receiverInfo.city}</div>

                        <div className="progress">{shipmentInfo.isDelivered && <div className="progress-bar" />}</div>

                        <div className="bs-wizard-dot" />

                        <div className="bs-wizard-dot-green">
                          <span className="glyphicon glyphicon-ok txt-white tick" />
                        </div>

                        <div className="bs-wizard-info text-center"> Delivered</div>
                      </div>
                    </div>
                  </div>
                </Col>
              </Row>

              <Row>
                <Col sm="12" md={{ size: 8, offset: 2 }}>
                  <h4>
                    <Translate contentKey="cargotrackerApp.shipmentTracking.public.trackingDetails">Tracking Details</Translate>
                  </h4>

                  <div className="table-responsive">
                    <Table responsive hover bordered>
                      <thead>
                        <tr>
                          <th className="hand">
                            <Translate contentKey="cargotrackerApp.shipmentTracking.trackingDate">Tracking Date</Translate>{' '}
                          </th>
                          <th className="hand">
                            <Translate contentKey="cargotrackerApp.shipmentTracking.trackingTime">Tracking Time</Translate>{' '}
                          </th>
                          <th className="hand">
                            <Translate contentKey="cargotrackerApp.shipmentTracking.place">Place</Translate>
                          </th>
                          <th className="hand">
                            <Translate contentKey="cargotrackerApp.shipmentTracking.status">Status</Translate>
                          </th>
                        </tr>
                      </thead>
                      <tbody>
                        {shipmentInfo.shipmentTrackings.map((shipmentTracking, i) => (
                          <tr key={`entity-${i}`}>
                            <td>
                              <TextFormat type="date" value={shipmentTracking.trackingDate} format={APP_DATEONLY_FORMAT} />
                            </td>
                            <td>
                              <TextFormat type="date" value={shipmentTracking.trackingDate} format={APP_TIMEONLY_FORMAT} />
                            </td>
                            <td>{shipmentTracking.place}</td>
                            <td>{shipmentTracking.status}</td>
                          </tr>
                        ))}
                      </tbody>
                    </Table>
                  </div>
                </Col>
              </Row>
            </Col>
          )}
      </Row>
    );
  }
}

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
  shipmentInfo: storeState.shipmentInformation.entity
});

const mapDispatchToProps = { getSession, getSearchWithConsignmentNo, reset };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);
