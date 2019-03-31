import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shipment-mode.reducer';
import { IShipmentMode } from 'app/shared/model/shipment-mode.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShipmentModeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ShipmentModeDetail extends React.Component<IShipmentModeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { shipmentModeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="cargotrackerApp.shipmentMode.detail.title">ShipmentMode</Translate> [<b>{shipmentModeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="value">
                <Translate contentKey="cargotrackerApp.shipmentMode.value">Value</Translate>
              </span>
            </dt>
            <dd>{shipmentModeEntity.value}</dd>
            <dt>
              <span id="desc">
                <Translate contentKey="cargotrackerApp.shipmentMode.desc">Desc</Translate>
              </span>
            </dt>
            <dd>{shipmentModeEntity.desc}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.shipmentMode.vendor">Vendor</Translate>
            </dt>
            <dd>{shipmentModeEntity.vendorId ? shipmentModeEntity.vendorId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/shipment-mode" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/shipment-mode/${shipmentModeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ shipmentMode }: IRootState) => ({
  shipmentModeEntity: shipmentMode.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentModeDetail);
