import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shipment-info.reducer';
import { IShipmentInfo } from 'app/shared/model/shipment-info.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShipmentInfoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ShipmentInfoDetail extends React.Component<IShipmentInfoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { shipmentInfoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="cargotrackerApp.shipmentInfo.detail.title">ShipmentInfo</Translate> [<b>{shipmentInfoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="consignmentNo">
                <Translate contentKey="cargotrackerApp.shipmentInfo.consignmentNo">Consignment No</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.consignmentNo}</dd>
            <dt>
              <span id="isThirdParty">
                <Translate contentKey="cargotrackerApp.shipmentInfo.isThirdParty">Is Third Party</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.isThirdParty ? 'true' : 'false'}</dd>
            <dt>
              <span id="carrier">
                <Translate contentKey="cargotrackerApp.shipmentInfo.carrier">Carrier</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.carrier}</dd>
            <dt>
              <span id="courier">
                <Translate contentKey="cargotrackerApp.shipmentInfo.courier">Courier</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.courier}</dd>
            <dt>
              <span id="carrierRefNo">
                <Translate contentKey="cargotrackerApp.shipmentInfo.carrierRefNo">Carrier Ref No</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.carrierRefNo}</dd>
            <dt>
              <span id="shipmentType">
                <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentType">Shipment Type</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.shipmentType}</dd>
            <dt>
              <span id="shipmentMode">
                <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentMode">Shipment Mode</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.shipmentMode}</dd>
            <dt>
              <span id="origin">
                <Translate contentKey="cargotrackerApp.shipmentInfo.origin">Origin</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.origin}</dd>
            <dt>
              <span id="destination">
                <Translate contentKey="cargotrackerApp.shipmentInfo.destination">Destination</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.destination}</dd>
            <dt>
              <span id="pickupDate">
                <Translate contentKey="cargotrackerApp.shipmentInfo.pickupDate">Pickup Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={shipmentInfoEntity.pickupDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="expectedDeliveryDate">
                <Translate contentKey="cargotrackerApp.shipmentInfo.expectedDeliveryDate">Expected Delivery Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={shipmentInfoEntity.expectedDeliveryDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="weight">
                <Translate contentKey="cargotrackerApp.shipmentInfo.weight">Weight</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.weight}</dd>
            <dt>
              <span id="quantity">
                <Translate contentKey="cargotrackerApp.shipmentInfo.quantity">Quantity</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.quantity}</dd>
            <dt>
              <span id="totalFright">
                <Translate contentKey="cargotrackerApp.shipmentInfo.totalFright">Total Fright</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.totalFright}</dd>
            <dt>
              <span id="packageDesciption">
                <Translate contentKey="cargotrackerApp.shipmentInfo.packageDesciption">Package Desciption</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.packageDesciption}</dd>
            <dt>
              <span id="paymentMode">
                <Translate contentKey="cargotrackerApp.shipmentInfo.paymentMode">Payment Mode</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.paymentMode}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="cargotrackerApp.shipmentInfo.status">Status</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.status}</dd>
            <dt>
              <span id="comments">
                <Translate contentKey="cargotrackerApp.shipmentInfo.comments">Comments</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoEntity.comments}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.shipmentInfo.vendor">Vendor</Translate>
            </dt>
            <dd>{shipmentInfoEntity.vendor ? shipmentInfoEntity.vendor.id : ''}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.shipmentInfo.origin">Origin</Translate>
            </dt>
            <dd>{shipmentInfoEntity.origin ? shipmentInfoEntity.origin.id : ''}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.shipmentInfo.destination">Destination</Translate>
            </dt>
            <dd>{shipmentInfoEntity.destination ? shipmentInfoEntity.destination.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/shipment-info" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/shipment-info/${shipmentInfoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ shipmentInfo }: IRootState) => ({
  shipmentInfoEntity: shipmentInfo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentInfoDetail);
