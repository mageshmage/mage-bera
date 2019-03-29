import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shipment-type.reducer';
import { IShipmentType } from 'app/shared/model/shipment-type.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShipmentTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ShipmentTypeDetail extends React.Component<IShipmentTypeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { shipmentTypeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="cargotrackerApp.shipmentType.detail.title">ShipmentType</Translate> [<b>{shipmentTypeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="value">
                <Translate contentKey="cargotrackerApp.shipmentType.value">Value</Translate>
              </span>
            </dt>
            <dd>{shipmentTypeEntity.value}</dd>
            <dt>
              <span id="desc">
                <Translate contentKey="cargotrackerApp.shipmentType.desc">Desc</Translate>
              </span>
            </dt>
            <dd>{shipmentTypeEntity.desc}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.shipmentType.vendor">Vendor</Translate>
            </dt>
            <dd>{shipmentTypeEntity.vendor ? shipmentTypeEntity.vendor.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/shipment-type" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/shipment-type/${shipmentTypeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ shipmentType }: IRootState) => ({
  shipmentTypeEntity: shipmentType.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentTypeDetail);
