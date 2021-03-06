import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
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
import { getSearchEntities, getEntities } from './shipment-info.reducer';
import { IShipmentInfo } from 'app/shared/model/shipment-info.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IShipmentInfoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IShipmentInfoState extends IPaginationBaseState {
  search: string;
}

export class ShipmentInfo extends React.Component<IShipmentInfoProps, IShipmentInfoState> {
  state: IShipmentInfoState = {
    search: '',
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
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
    if (search) {
      this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
    } else {
      this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
    }
  };

  render() {
    const { shipmentInfoList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="shipment-info-heading">
          <Translate contentKey="cargotrackerApp.shipmentInfo.home.title">Shipment Infos</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cargotrackerApp.shipmentInfo.home.createLabel">Create new Shipment Info</Translate>
          </Link>
        </h2>
        <Row>
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
        </Row>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('consignmentNo')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.consignmentNo">Consignment No</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('bookingDate')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.bookingDate">Booking Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('expectedDeliveryDate')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.expectedDeliveryDate">Expected Delivery Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('actualWeight')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.actualWeight">Actual Weight</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('volumetricWeight')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.volumetricWeight">Volumetric Weight</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('length')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.length">Length</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('width')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.width">Width</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('height')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.height">Height</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('quantity')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.quantity">Quantity</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('totalFright')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.totalFright">Total Fright</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('packageDesciption')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.packageDesciption">Package Desciption</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('isThirdParty')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.isThirdParty">Is Third Party</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('carrierRefNo')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.carrierRefNo">Carrier Ref No</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('deliveredDate')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.deliveredDate">Delivered Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('receivedBy')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.receivedBy">Received By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('relationShip')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.relationShip">Relation Ship</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.carrierDetails">Carrier Details</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentType">Shipment Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentMode">Shipment Mode</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.paymentMode">Payment Mode</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.trackingStatus">Tracking Status</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.vendor">Vendor</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.origin">Origin</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.destination">Destination</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {shipmentInfoList.map((shipmentInfo, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${shipmentInfo.id}`} color="link" size="sm">
                      {shipmentInfo.id}
                    </Button>
                  </td>
                  <td>{shipmentInfo.consignmentNo}</td>
                  <td>
                    <TextFormat type="date" value={shipmentInfo.bookingDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={shipmentInfo.expectedDeliveryDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{shipmentInfo.actualWeight}</td>
                  <td>{shipmentInfo.volumetricWeight}</td>
                  <td>{shipmentInfo.length}</td>
                  <td>{shipmentInfo.width}</td>
                  <td>{shipmentInfo.height}</td>
                  <td>{shipmentInfo.quantity}</td>
                  <td>{shipmentInfo.totalFright}</td>
                  <td>{shipmentInfo.packageDesciption}</td>
                  <td>{shipmentInfo.isThirdParty ? 'true' : 'false'}</td>
                  <td>{shipmentInfo.carrierRefNo}</td>
                  <td>
                    <TextFormat type="date" value={shipmentInfo.deliveredDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{shipmentInfo.receivedBy}</td>
                  <td>{shipmentInfo.relationShip}</td>
                  <td>
                    {shipmentInfo.carrierDetailsId ? (
                      <Link to={`carrier-details/${shipmentInfo.carrierDetailsId}`}>{shipmentInfo.carrierDetailsValue}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {shipmentInfo.shipmentTypeId ? (
                      <Link to={`shipment-type/${shipmentInfo.shipmentTypeId}`}>{shipmentInfo.shipmentTypeValue}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {shipmentInfo.shipmentModeId ? (
                      <Link to={`shipment-mode/${shipmentInfo.shipmentModeId}`}>{shipmentInfo.shipmentModeValue}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {shipmentInfo.paymentModeId ? (
                      <Link to={`payment-mode/${shipmentInfo.paymentModeId}`}>{shipmentInfo.paymentModeValue}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {shipmentInfo.trackingStatusId ? (
                      <Link to={`tracking-status/${shipmentInfo.trackingStatusId}`}>{shipmentInfo.trackingStatusValue}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{shipmentInfo.vendorId ? <Link to={`vendor/${shipmentInfo.vendorId}`}>{shipmentInfo.vendorname}</Link> : ''}</td>
                  <td>{shipmentInfo.originId ? <Link to={`state/${shipmentInfo.originId}`}>{shipmentInfo.originValue}</Link> : ''}</td>
                  <td>
                    {shipmentInfo.destinationId ? (
                      <Link to={`state/${shipmentInfo.destinationId}`}>{shipmentInfo.destinationValue}</Link>
                    ) : (
                      ''
                    )}
                  </td>
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

const mapStateToProps = ({ shipmentInfo }: IRootState) => ({
  shipmentInfoList: shipmentInfo.entities,
  totalItems: shipmentInfo.totalItems
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentInfo);
