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
import { getSearchEntities, getEntities, negateEnable, updateSearch } from './shipment-tracking.reducer';
import { IShipmentTracking } from 'app/shared/model/shipment-tracking.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT, APP_DATEONLY_FORMAT, APP_TIMEONLY_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IShipmentTrackingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IShipmentTrackingState extends IPaginationBaseState {
  search: string;
}

export class ShipmentTracking extends React.Component<IShipmentTrackingProps, IShipmentTrackingState> {
  state: IShipmentTrackingState = {
    search: '',
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    //this.getEntities();
    //alert('mount');
    if (this.props.searchValue) {
      this.setState({ search: this.props.searchValue });
      this.setState({ activePage: 1 }, () => {
        const { activePage, itemsPerPage, sort, order, search } = this.state;
        const { vendorId } = this.props;
        this.props.updateSearch(this.state.search);
        //alert('search')
        this.props.getSearchEntities(search, vendorId, activePage - 1, itemsPerPage, `${sort},${order}`);
      });
    }
  }

  search = () => {
    //alert('search - ' + this.state.search)
    if (this.state.search) {
      this.setState({ activePage: 1 }, () => {
        const { activePage, itemsPerPage, sort, order, search } = this.state;
        const { vendorId } = this.props;
        this.props.updateSearch(this.state.search);
        //alert('search')
        this.props.getSearchEntities(search, vendorId, activePage - 1, itemsPerPage, `${sort},${order}`);
      });
    }
  };

  clear = () => {
    this.setState({ search: '', activePage: 1 }, () => {
      //this.props.getEntities();
      this.props.negateEnable();
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
    //this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order, search } = this.state;
    if (search) {
      const { vendorId } = this.props;
      this.props.getSearchEntities(search, vendorId, activePage - 1, itemsPerPage, `${sort},${order}`);
    } else {
      //this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
    }
  };

  render() {
    const { shipmentTrackingList, match, totalItems, isEnable } = this.props;
    return (
      <div>
        <h2 id="shipment-tracking-heading">
          <Translate contentKey="cargotrackerApp.shipmentTracking.home.title">Shipment Trackings</Translate>
        </h2>
        <Row>
          <Col sm="4">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('cargotrackerApp.shipmentTracking.home.search')}
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
          <Col sm="4">
            {shipmentTrackingList.length > 0 &&
              !shipmentTrackingList[0].isDelivered && (
                <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
                  <FontAwesomeIcon icon="plus" />
                  &nbsp;
                  <Translate contentKey="cargotrackerApp.shipmentTracking.home.createLabel">Create new Shipment Tracking</Translate>
                </Link>
              )}

            {shipmentTrackingList.length > 0 &&
              shipmentTrackingList[0].isDelivered && (
                <>
                  <div className="col-sm-1">
                    <FontAwesomeIcon icon="check-circle" size="2x" color="green" title="Verified" />
                  </div>
                </>
              )}
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
                {/*<th>
                  <Translate contentKey="cargotrackerApp.shipmentTracking.shipmentInfo">Shipment Info</Translate>{' '}

                </th>*/}
                <th />
              </tr>
            </thead>
            <tbody>
              {shipmentTrackingList.map((shipmentTracking, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    {/*<Button tag={Link} to={`${match.url}/${shipmentTracking.id}`} color="link" size="sm">
                      {shipmentTracking.id}
              </Button>*/}
                    {i + 1}
                  </td>
                  <td>
                    <TextFormat type="date" value={shipmentTracking.trackingDate} format={APP_DATEONLY_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={shipmentTracking.trackingDate} format={APP_TIMEONLY_FORMAT} />
                  </td>
                  <td>{shipmentTracking.place}</td>
                  <td>{shipmentTracking.status}</td>
                  {/*<td>
                    {shipmentTracking.shipmentInfoId ? (
                      <Link to={`shipment-info/${shipmentTracking.shipmentInfoId}`}>{shipmentTracking.shipmentInfoId}</Link>
                    ) : (
                        ''
                    )}
                  </td>*/}
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${shipmentTracking.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shipmentTracking.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shipmentTracking.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ shipmentTrackingSolo, authentication }: IRootState) => ({
  shipmentTrackingList: shipmentTrackingSolo.entities,
  totalItems: shipmentTrackingSolo.totalItems,
  isEnable: shipmentTrackingSolo.isEnable,
  vendorId: authentication.account.vendorId,
  searchValue: shipmentTrackingSolo.search
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  negateEnable,
  updateSearch
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentTracking);
