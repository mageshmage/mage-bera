import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './carrier-details.reducer';
import { ICarrierDetails } from 'app/shared/model/carrier-details.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICarrierDetailsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface ICarrierDetailsState {
  search: string;
}

export class CarrierDetails extends React.Component<ICarrierDetailsProps, ICarrierDetailsState> {
  state: ICarrierDetailsState = {
    search: ''
  };

  componentDidMount() {
    this.props.getEntities();
  }

  search = () => {
    if (this.state.search) {
      this.props.getSearchEntities(this.state.search);
    }
  };

  clear = () => {
    this.setState({ search: '' }, () => {
      this.props.getEntities();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { carrierDetailsList, match } = this.props;
    return (
      <div>
        <h2 id="carrier-details-heading">
          <Translate contentKey="cargotrackerApp.carrierDetails.home.title">Carrier Details</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cargotrackerApp.carrierDetails.home.createLabel">Create new Carrier Details</Translate>
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
                    placeholder={translate('cargotrackerApp.carrierDetails.home.search')}
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
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.carrierDetails.value">Value</Translate>
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.carrierDetails.desc">Desc</Translate>
                </th>
                <th>
                  <Translate contentKey="cargotrackerApp.carrierDetails.vendor">Vendor</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {carrierDetailsList.map((carrierDetails, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${carrierDetails.id}`} color="link" size="sm">
                      {carrierDetails.id}
                    </Button>
                  </td>
                  <td>{carrierDetails.value}</td>
                  <td>{carrierDetails.desc}</td>
                  <td>
                    {carrierDetails.vendorId ? <Link to={`vendor/${carrierDetails.vendorId}`}>{carrierDetails.vendorname}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${carrierDetails.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${carrierDetails.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${carrierDetails.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ carrierDetails }: IRootState) => ({
  carrierDetailsList: carrierDetails.entities
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
)(CarrierDetails);
