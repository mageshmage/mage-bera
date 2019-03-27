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
            <FontAwesomeIcon icon="plus" />&nbsp;
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
                <th className="hand" onClick={this.sort('isThirdParty')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.isThirdParty">Is Third Party</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('carrier')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.carrier">Carrier</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('courier')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.courier">Courier</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('carrierRefNo')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.carrierRefNo">Carrier Ref No</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('shipmentType')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentType">Shipment Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('shipmentMode')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentMode">Shipment Mode</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('pickupDate')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.pickupDate">Pickup Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('expectedDeliveryDate')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.expectedDeliveryDate">Expected Delivery Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('weight')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.weight">Weight</Translate> <FontAwesomeIcon icon="sort" />
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
                <th className="hand" onClick={this.sort('paymentMode')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.paymentMode">Payment Mode</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('status')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('comments')}>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.comments">Comments</Translate> <FontAwesomeIcon icon="sort" />
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
                  <td>{shipmentInfo.isThirdParty ? 'true' : 'false'}</td>
                  <td>
                    <Translate contentKey={`cargotrackerApp.CARRIER.${shipmentInfo.carrier}`} />
                  </td>
                  <td>{shipmentInfo.courier}</td>
                  <td>{shipmentInfo.carrierRefNo}</td>
                  <td>
                    <Translate contentKey={`cargotrackerApp.ShipmentType.${shipmentInfo.shipmentType}`} />
                  </td>
                  <td>
                    <Translate contentKey={`cargotrackerApp.ShipmentMode.${shipmentInfo.shipmentMode}`} />
                  </td>
                  <td>
                    <TextFormat type="date" value={shipmentInfo.pickupDate} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={shipmentInfo.expectedDeliveryDate} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{shipmentInfo.weight}</td>
                  <td>{shipmentInfo.quantity}</td>
                  <td>{shipmentInfo.totalFright}</td>
                  <td>{shipmentInfo.packageDesciption}</td>
                  <td>
                    <Translate contentKey={`cargotrackerApp.PaymentMode.${shipmentInfo.paymentMode}`} />
                  </td>
                  <td>
                    <Translate contentKey={`cargotrackerApp.Status.${shipmentInfo.status}`} />
                  </td>
                  <td>{shipmentInfo.comments}</td>
                  <td>{shipmentInfo.vendor ? <Link to={`vendor/${shipmentInfo.vendor.id}`}>{shipmentInfo.vendor.id}</Link> : ''}</td>
                  <td>{shipmentInfo.origin ? <Link to={`state/${shipmentInfo.origin.id}`}>{shipmentInfo.origin.id}</Link> : ''}</td>
                  <td>
                    {shipmentInfo.destination ? <Link to={`state/${shipmentInfo.destination.id}`}>{shipmentInfo.destination.id}</Link> : ''}
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
