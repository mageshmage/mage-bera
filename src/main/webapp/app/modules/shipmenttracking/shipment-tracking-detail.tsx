import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shipment-tracking.reducer';
import { IShipmentTracking } from 'app/shared/model/shipment-tracking.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShipmentTrackingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ShipmentTrackingDetail extends React.Component<IShipmentTrackingDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { shipmentTrackingEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2 className="cargoTitle">
            <Translate contentKey="cargotrackerApp.shipmentTracking.detail.title">ShipmentTracking</Translate> [
            <b>{shipmentTrackingEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="trackingDate">
                <Translate contentKey="cargotrackerApp.shipmentTracking.trackingDate">Tracking Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={shipmentTrackingEntity.trackingDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="place">
                <Translate contentKey="cargotrackerApp.shipmentTracking.place">Place</Translate>
              </span>
            </dt>
            <dd>{shipmentTrackingEntity.place}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="cargotrackerApp.shipmentTracking.status">Status</Translate>
              </span>
            </dt>
            <dd>{shipmentTrackingEntity.status}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.shipmentTracking.shipmentInfo">Shipment Info</Translate>
            </dt>
            <dd>{shipmentTrackingEntity.shipmentInfoId ? shipmentTrackingEntity.shipmentInfoId : ''}</dd>
          </dl>
          <Button tag={Link} to="/shipment-tracking" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/shipment-tracking/${shipmentTrackingEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ shipmentTrackingSolo }: IRootState) => ({
  shipmentTrackingEntity: shipmentTrackingSolo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentTrackingDetail);
