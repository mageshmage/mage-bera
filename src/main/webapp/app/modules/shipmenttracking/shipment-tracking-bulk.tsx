import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { createEntityBulk, reset } from 'app/modules/shipmenttracking/shipment-tracking.reducer';

export interface IShipmentTrackingBulkProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IShipmentTrackingBulkState {
  isNew: boolean;
  shipmentInfoId: string;
  data: FormData;
  value: string;
}

export class ShipmentTrackingBulk extends React.Component<IShipmentTrackingBulkProps, IShipmentTrackingBulkState> {
  constructor(props) {
    super(props);
    this.state = {
      shipmentInfoId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id,
      data: null,
      value: ''
    };
  }

  componentWillUpdate(nextProps, nextState) {}

  componentDidMount() {}

  handleUploadFile = event => {
    this.clear();
    const data = new FormData();
    //using File API to get chosen file
    console.log('Uploading file', event.target.files[0]);
    data.append('file', event.target.files[0]);
    data.append('vendorId', this.props.vendorId);
    this.setState({ data: data, value: event.target.value });
  };

  clear = () => {
    this.setState({ data: null, value: '' });
    this.props.reset();
  };

  createEntityBulk = () => {
    alert('createEntityBulk');
    this.props.createEntityBulk(this.state.data);
  };

  render() {
    const { bulkResponse } = this.props;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="12">
            <h2 id="cargotrackerApp.shipmentTracking.home.titleBulk" className="cargoTitle">
              <Translate contentKey="cargotrackerApp.shipmentTracking.home.titleBulk">Create Shipment Bulk</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="12">
            <div>
              <input type="file" value={this.state.value} onChange={this.handleUploadFile} />
              <Button type="reset" className="input-group-addon" onClick={this.createEntityBulk} replace color="primary">
                <FontAwesomeIcon icon="upload" />
              </Button>
              &nbsp;
              <Button type="reset" className="input-group-addon" onClick={this.clear} replace color="danger">
                <FontAwesomeIcon icon="trash" />
              </Button>
              &nbsp;
              <Button tag={Link} to="/shipment-tracking" replace color="info">
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Close</Translate>
                </span>
              </Button>
              &nbsp;
            </div>
          </Col>
        </Row>
        {bulkResponse &&
          bulkResponse.isError != null &&
          bulkResponse.isError && (
            <div className="table-responsive">
              <Table responsive hover bordered>
                <thead>
                  <tr>
                    <th className="hand">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </th>
                    <th className="hand">Error </th>
                  </tr>
                </thead>
                <tbody>
                  {bulkResponse.errorList.map((errorStr, i) => (
                    <tr key={`entity-${i}`}>
                      <td>{i + 1}</td>
                      <td>{errorStr}</td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            </div>
          )}
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  loading: storeState.shipmentInfoPODSolo.loading,
  updating: storeState.shipmentInfoPODSolo.updating,
  updateSuccess: storeState.shipmentInfoPODSolo.updateSuccess,
  vendorId: storeState.authentication.account.vendorId,
  bulkResponse: storeState.shipmentTrackingSolo.bulkResponse
});

const mapDispatchToProps = {
  createEntityBulk,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentTrackingBulk);
