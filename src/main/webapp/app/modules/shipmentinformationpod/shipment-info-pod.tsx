import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { openFile, byteSize, Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, updateSearch, negate } from './shipment-info-pod.reducer';
import { IShipmentInfoPOD } from 'app/shared/model/shipment-info-pod.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShipmentInfoPODProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IShipmentInfoPODState {
  search: string;
}

export class ShipmentInfoPOD extends React.Component<IShipmentInfoPODProps, IShipmentInfoPODState> {
  state: IShipmentInfoPODState = {
    search: ''
  };

  componentDidMount() {
    if (this.props.searchValue) {
      this.setState({ search: this.props.searchValue });
      //this.props.getEntities();
      const { search } = this.state;
      const { vendorId } = this.props;
      this.props.updateSearch(this.state.search);
      this.props.getSearchEntities(this.props.searchValue, vendorId);
    }
  }

  search = () => {
    if (this.state.search) {
      const { vendorId } = this.props;
      this.props.updateSearch(this.state.search);
      this.props.getSearchEntities(this.state.search, vendorId);
    }
  };

  clear = () => {
    this.setState({ search: '' }, () => {
      //this.props.getEntities();
      this.props.negate();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { shipmentInfoPODList, match, isEnable } = this.props;
    return (
      <div>
        <h2 id="shipment-info-pod-heading">
          <Translate contentKey="cargotrackerApp.shipmentInfoPOD.home.title">Shipment Info PODS</Translate>
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
                    placeholder={translate('cargotrackerApp.shipmentInfoPOD.home.search')}
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
            {shipmentInfoPODList.length === 0 &&
              isEnable === true && (
                <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
                  <FontAwesomeIcon icon="plus" />
                  &nbsp;
                  <Translate contentKey="cargotrackerApp.shipmentInfoPOD.home.createLabel">Create new Shipment Info POD</Translate>
                </Link>
              )}
          </Col>
        </Row>
        <div className="table-responsive">
          <Table responsive hover bordered>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfoPOD.pod">Pod</Translate>
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.shipmentInfoPOD.comments">Comments</Translate>
                </th>
                {/*<th>
                  <Translate contentKey="cargotrackerApp.shipmentInfoPOD.shipmentInfo">Shipment Info</Translate>
                </th>*/}
                <th />
              </tr>
            </thead>
            <tbody>
              {shipmentInfoPODList.map((shipmentInfoPOD, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    {/*<Button tag={Link} to={`${match.url}/${shipmentInfoPOD.id}`} color="link" size="sm">
                      {shipmentInfoPOD.id}
              </Button>*/}
                    {i + 1}
                  </td>
                  <td>
                    {shipmentInfoPOD.pod ? (
                      <div>
                        <a onClick={openFile(shipmentInfoPOD.podContentType, shipmentInfoPOD.pod)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                          &nbsp;
                        </a>
                        <span>
                          {shipmentInfoPOD.podContentType}, {byteSize(shipmentInfoPOD.pod)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{shipmentInfoPOD.comments}</td>
                  {/*<td>
                    {shipmentInfoPOD.shipmentInfoId ? (
                      <Link to={`shipment-info/${shipmentInfoPOD.shipmentInfoId}`}>{shipmentInfoPOD.shipmentInfoId}</Link>
                    ) : (
                        ''
                      )}
                    </td>*/}
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${shipmentInfoPOD.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shipmentInfoPOD.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shipmentInfoPOD.id}/delete`} color="danger" size="sm">
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
      </div>
    );
  }
}

const mapStateToProps = ({ shipmentInfoPODSolo, authentication }: IRootState) => ({
  shipmentInfoPODList: shipmentInfoPODSolo.entities,
  vendorId: authentication.account.vendorId,
  searchValue: shipmentInfoPODSolo.search,
  isEnable: shipmentInfoPODSolo.isEnable
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  updateSearch,
  negate
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentInfoPOD);
