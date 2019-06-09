import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, FormGroup, Card, CardBody, CardTitle, Table, CardFooter } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import {
  Translate,
  translate,
  ICrudSearchAction,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  getPaginationItemsNumber,
  JhiPagination
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, getEntitiesSearch } from './shipment-information.reducer';
import {
  IShipmentInfo,
  IShipmentInformationSearchDTO,
  defaultValueShipmentInformationSearchDTO
} from 'app/shared/model/shipment-info.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { getCarierDetailsByVendorId } from 'app/entities/carrier-details/carrier-details.reducer';
import { getShipmentTypesByVendorId } from 'app/entities/shipment-type/shipment-type.reducer';
import { getShipmentModesByVendorId } from 'app/entities/shipment-mode/shipment-mode.reducer';
import { getPaymentModesByVendorId } from 'app/entities/payment-mode/payment-mode.reducer';
import { getTrackingStatusByVendorId } from 'app/entities/tracking-status/tracking-status.reducer';
import { getEntities as getStates } from 'app/entities/state/state.reducer';

export interface IShipmentInformationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IShipmentInformationState extends IPaginationBaseState {
  search: string;
}

export class ShipmentInformation extends React.Component<IShipmentInformationProps, IShipmentInformationState> {
  state: IShipmentInformationState = {
    search: '',
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();

    const { vendorId, carrierDetails, shipmentTypes, shipmentModes, paymentModes, trackingStatuses, states } = this.props;

    if (vendorId !== undefined && vendorId !== null) {
      if (carrierDetails !== undefined && carrierDetails.length === 0) {
        this.props.getCarierDetailsByVendorId(this.props.vendorId);
      }
      if (shipmentTypes !== undefined && shipmentTypes.length === 0) {
        this.props.getShipmentTypesByVendorId(this.props.vendorId);
      }
      if (shipmentModes !== undefined && shipmentModes.length === 0) {
        this.props.getShipmentModesByVendorId(this.props.vendorId);
      }
      if (paymentModes !== undefined && paymentModes.length === 0) {
        this.props.getPaymentModesByVendorId(this.props.vendorId);
      }
      if (trackingStatuses !== undefined && trackingStatuses.length === 0) {
        this.props.getTrackingStatusByVendorId(this.props.vendorId);
      }
      if (states !== undefined && states.length === 0) {
        this.props.getStates();
      }
    }
  }

  search = () => {
    if (this.state.search) {
      this.setState({ activePage: 1 }, () => {
        const { activePage, itemsPerPage, sort, order, search } = this.state;
        this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
      });
    }
  };

  clear = () => {
    this.setState({ search: '', activePage: 1 }, () => {
      this.props.getEntities();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order, search } = this.state;
    const { vendorId } = this.props;
    // alert('vendorId - ' + vendorId);
    const searchData: IShipmentInformationSearchDTO = defaultValueShipmentInformationSearchDTO;
    searchData.vendorId = vendorId;

    if (search) {
      this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
    } else {
      this.props.getEntitiesSearch(searchData, activePage - 1, itemsPerPage, `${sort},${order}`);
    }
  };

  searchEntities = (event, errors, values) => {
    const { activePage, itemsPerPage, sort, order, search } = this.state;
    const { vendorId } = this.props;

    values.bookingDateFrom = convertDateTimeToServer(values.bookingDateFrom);
    values.bookingDateTo = convertDateTimeToServer(values.bookingDateTo);
    values.expectedDeliveryDateFrom = convertDateTimeToServer(values.expectedDeliveryDateFrom);
    values.expectedDeliveryDateTo = convertDateTimeToServer(values.expectedDeliveryDateTo);
    values.deliveredDateFrom = convertDateTimeToServer(values.deliveredDateFrom);
    values.deliveredDateTo = convertDateTimeToServer(values.deliveredDateTo);

    const searchData: IShipmentInformationSearchDTO = defaultValueShipmentInformationSearchDTO;
    searchData.vendorId = vendorId;

    const entity = {
      ...searchData,
      ...values
    };

    this.props.getEntitiesSearch(entity, activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { shipmentInfoList, match, totalItems } = this.props;
    const { carrierDetails, shipmentTypes, shipmentModes, paymentModes, trackingStatuses, states, loading, updating } = this.props;
    return (
      <div>
        <h2 id="shipment-info-heading" className="cargoTitle">
          <Translate contentKey="cargotrackerApp.shipmentInfo.home.title">Shipment Infos</Translate>
        </h2>
        {/*<Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('cargotrackerApp.shipmentInfo.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>*/}

        <Row className="justify-content-center">
          <Col md="12">
            <AvForm model={defaultValueShipmentInformationSearchDTO} onSubmit={this.searchEntities}>
              <Card>
                {/*<CardTitle>Shipment Details Search</CardTitle>*/}
                <CardBody>
                  <Row>
                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label id="consignmentNoLabel" for="consignmentNo" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.consignmentNo">Consignment No : </Translate> :
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvField id="shipment-info-consignmentNo" type="text" name="consignmentNo" />
                        </Col>
                      </FormGroup>
                    </Col>
                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label for="trackingStatus.id" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.trackingStatus">Tracking Status</Translate> :
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvInput id="shipment-info-trackingStatus" type="select" className="form-control" name="trackingStatusId">
                            <option value={-1} key={-1}>
                              --Select--
                            </option>
                            {trackingStatuses
                              ? trackingStatuses.map(otherEntity => (
                                  <option value={otherEntity.id} key={otherEntity.id}>
                                    {otherEntity.value}
                                  </option>
                                ))
                              : null}
                          </AvInput>
                        </Col>
                      </FormGroup>
                    </Col>
                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label for="origin.id" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.origin">Origin</Translate>
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvInput id="shipment-info-origin" type="select" className="form-control" name="originId">
                            <option value={-1} key={-1}>
                              --Select--
                            </option>
                            {states
                              ? states.map(otherEntity => (
                                  <option value={otherEntity.id} key={otherEntity.id}>
                                    {otherEntity.stateName}
                                  </option>
                                ))
                              : null}
                          </AvInput>
                        </Col>
                      </FormGroup>
                    </Col>
                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label for="destination.id" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.destination">Destination</Translate>
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvInput id="shipment-info-destination" type="select" className="form-control" name="destinationId">
                            <option value={-1} key={-1}>
                              --Select--
                            </option>
                            {states
                              ? states.map(otherEntity => (
                                  <option value={otherEntity.id} key={otherEntity.id}>
                                    {otherEntity.stateName}
                                  </option>
                                ))
                              : null}
                          </AvInput>
                        </Col>
                      </FormGroup>
                    </Col>
                  </Row>

                  <Row>
                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label id="bookingDateFromLabel" for="bookingDateFrom" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.bookingDateFrom">Booking Date From:</Translate> :
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvInput
                            id="shipment-info-bookingDateFrom"
                            type="date"
                            className="form-control"
                            name="bookingDateFrom"
                            placeholder={'YYYY-MM-DD HH:mm'}
                            value={null}
                          />
                        </Col>
                      </FormGroup>
                    </Col>

                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label id="bookingDateToLabel" for="bookingDateTo" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.bookingDateTo">Booking Date To:</Translate> :
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvInput
                            id="shipment-info-bookingDateTo"
                            type="date"
                            className="form-control"
                            name="bookingDateTo"
                            placeholder={'YYYY-MM-DD HH:mm'}
                            value={null}
                          />
                        </Col>
                      </FormGroup>
                    </Col>

                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label id="expectedDeliveryDateFromLabel" for="expectedDeliveryDateFrom" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.expectedDeliveryDateFrom">
                              Expected Delivery Date From
                            </Translate>{' '}
                            :
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvInput
                            id="shipment-info-expectedDeliveryDateFrom"
                            type="date"
                            className="form-control"
                            name="expectedDeliveryDateFrom"
                            placeholder={'YYYY-MM-DD HH:mm'}
                            value={null}
                          />
                        </Col>
                      </FormGroup>
                    </Col>

                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label id="expectedDeliveryDateToLabel" for="expectedDeliveryDateTo" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.expectedDeliveryDateTo">
                              Expected Delivery Date To
                            </Translate>{' '}
                            :
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvInput
                            id="shipment-info-expectedDeliveryDateTo"
                            type="date"
                            className="form-expectedDeliveryDateTo"
                            name="expectedDeliveryDate"
                            placeholder={'YYYY-MM-DD HH:mm'}
                            value={null}
                          />
                        </Col>
                      </FormGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label for="carrierDetails.id" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.carrierDetails">Carrier Details</Translate>
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvInput id="shipment-info-carrierDetails" type="select" className="form-control" name="carrierDetailsId">
                            <option value={-1} key={-1}>
                              --Select--
                            </option>
                            {carrierDetails
                              ? carrierDetails.map(otherEntity => (
                                  <option value={otherEntity.id} key={otherEntity.id}>
                                    {otherEntity.value}
                                  </option>
                                ))
                              : null}
                          </AvInput>
                        </Col>
                      </FormGroup>
                    </Col>
                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label id="carrierRefNoLabel" for="carrierRefNo" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.carrierRefNo">Carrier Ref No</Translate>
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvField id="shipment-info-carrierRefNo" type="text" name="carrierRefNo" />
                        </Col>
                      </FormGroup>
                    </Col>
                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label for="shipmentType.id" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentType">Shipment Type</Translate> :
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvInput id="shipment-info-shipmentType" type="select" className="form-control" name="shipmentTypeId">
                            <option value={-1} key={-1}>
                              --Select--
                            </option>
                            {shipmentTypes
                              ? shipmentTypes.map(otherEntity => (
                                  <option value={otherEntity.id} key={otherEntity.id}>
                                    {otherEntity.value}
                                  </option>
                                ))
                              : null}
                          </AvInput>
                        </Col>
                      </FormGroup>
                    </Col>
                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label for="shipmentMode.id" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentMode">Shipment Mode</Translate> :
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvInput id="shipment-info-shipmentMode" type="select" className="form-control" name="shipmentModeId">
                            <option value={-1} key={-1}>
                              --Select--
                            </option>
                            {shipmentModes
                              ? shipmentModes.map(otherEntity => (
                                  <option value={otherEntity.id} key={otherEntity.id}>
                                    {otherEntity.value}
                                  </option>
                                ))
                              : null}
                          </AvInput>
                        </Col>
                      </FormGroup>
                    </Col>
                  </Row>
                  <Row>
                    <Col sm={3}>
                      <FormGroup row>
                        <Col sm={5}>
                          <Label for="paymentMode.id" className="cargotracker-label">
                            <Translate contentKey="cargotrackerApp.shipmentInfo.paymentMode">Payment Mode</Translate> :
                          </Label>
                        </Col>
                        <Col sm={7}>
                          <AvInput id="shipment-info-paymentMode" type="select" className="form-control" name="paymentModeId">
                            <option value={-1} key={-1}>
                              --Select--
                            </option>
                            {paymentModes
                              ? paymentModes.map(otherEntity => (
                                  <option value={otherEntity.id} key={otherEntity.id}>
                                    {otherEntity.value}
                                  </option>
                                ))
                              : null}
                          </AvInput>
                        </Col>
                      </FormGroup>
                    </Col>
                  </Row>
                </CardBody>
                <CardFooter>
                  <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                    <FontAwesomeIcon icon="search" />
                    &nbsp;
                    <Translate contentKey="entity.action.search">Search</Translate>
                  </Button>
                  &nbsp;
                  <Link to={`${match.url}/bulk`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="cargotrackerApp.shipmentInfo.home.createLabelBulk">Create new Shipment Info Bulk</Translate>
                  </Link>
                  &nbsp;
                  <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
                    <FontAwesomeIcon icon="plus" />
                    &nbsp;
                    <Translate contentKey="cargotrackerApp.shipmentInfo.home.createLabel">Create new Shipment Info</Translate>
                  </Link>
                </CardFooter>
              </Card>
            </AvForm>
          </Col>
        </Row>

        <div className="table-responsive">
          <Table responsive hover bordered>
            <thead>
              <tr>
                <th className="hand">
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th className="hand">
                  <Translate contentKey="cargotrackerApp.shipmentInfo.consignmentNo">Consignment No</Translate>{' '}
                </th>
                <th className="hand">
                  <Translate contentKey="cargotrackerApp.shipmentInfo.bookingDate">Booking Date</Translate>
                </th>
                <th className="hand">
                  <Translate contentKey="cargotrackerApp.shipmentInfo.expectedDeliveryDate">Expected Delivery Date</Translate>{' '}
                </th>

                <th className="hand">
                  <Translate contentKey="cargotrackerApp.shipmentInfo.deliveredDate">Delivered Date</Translate>{' '}
                </th>

                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.carrierDetails">Carrier Details</Translate>{' '}
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentType">Shipment Type</Translate>{' '}
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentMode">Shipment Mode</Translate>{' '}
                </th>

                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.trackingStatus">Tracking Status</Translate>{' '}
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.vendor">Vendor</Translate>
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.origin">Origin</Translate>
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.destination">Destination</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {shipmentInfoList.map((shipmentInfo, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    {/*<Button tag={Link} to={`${match.url}/${shipmentInfo.id}`} color="link" size="sm">
                      {shipmentInfo.id}
                    </Button>*/}
                    {i + 1}
                  </td>
                  <td>{shipmentInfo.consignmentNo}</td>
                  <td>
                    <TextFormat type="date" value={shipmentInfo.bookingDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={shipmentInfo.expectedDeliveryDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={shipmentInfo.deliveredDate} format={APP_DATE_FORMAT} />
                  </td>

                  <td>{shipmentInfo.carrierDetailsValue}</td>
                  <td>{shipmentInfo.shipmentTypeValue}</td>
                  <td>{shipmentInfo.shipmentModeValue}</td>

                  <td>{shipmentInfo.trackingStatusValue}</td>
                  <td>{shipmentInfo.vendorname}</td>
                  <td>{shipmentInfo.originValue}</td>
                  <td>{shipmentInfo.destinationValue}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${shipmentInfo.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shipmentInfo.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shipmentInfo.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  shipmentInfoList: storeState.shipmentInformation.entities,
  totalItems: storeState.shipmentInformation.totalItems,
  vendorId: storeState.authentication.account.vendorId,
  loading: storeState.shipmentInformation.loading,
  carrierDetails: storeState.carrierDetails.entities,
  shipmentTypes: storeState.shipmentType.entities,
  shipmentModes: storeState.shipmentMode.entities,
  paymentModes: storeState.paymentMode.entities,
  trackingStatuses: storeState.trackingStatus.entities,
  states: storeState.state.entities,
  updating: storeState.shipmentInformation.updating
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  getEntitiesSearch,

  getCarierDetailsByVendorId,
  getShipmentTypesByVendorId,
  getShipmentModesByVendorId,
  getPaymentModesByVendorId,
  getTrackingStatusByVendorId,
  getStates
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentInformation);
