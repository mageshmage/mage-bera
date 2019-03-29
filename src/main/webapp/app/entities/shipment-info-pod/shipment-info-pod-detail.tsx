import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shipment-info-pod.reducer';
import { IShipmentInfoPOD } from 'app/shared/model/shipment-info-pod.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShipmentInfoPODDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ShipmentInfoPODDetail extends React.Component<IShipmentInfoPODDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { shipmentInfoPODEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="cargotrackerApp.shipmentInfoPOD.detail.title">ShipmentInfoPOD</Translate> [<b>
              {shipmentInfoPODEntity.id}
            </b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="pod">
                <Translate contentKey="cargotrackerApp.shipmentInfoPOD.pod">Pod</Translate>
              </span>
            </dt>
            <dd>
              {shipmentInfoPODEntity.pod ? (
                <div>
                  <a onClick={openFile(shipmentInfoPODEntity.podContentType, shipmentInfoPODEntity.pod)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                  <span>
                    {shipmentInfoPODEntity.podContentType}, {byteSize(shipmentInfoPODEntity.pod)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="comments">
                <Translate contentKey="cargotrackerApp.shipmentInfoPOD.comments">Comments</Translate>
              </span>
            </dt>
            <dd>{shipmentInfoPODEntity.comments}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.shipmentInfoPOD.shipmentInfo">Shipment Info</Translate>
            </dt>
            <dd>{shipmentInfoPODEntity.shipmentInfo ? shipmentInfoPODEntity.shipmentInfo.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/shipment-info-pod" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/shipment-info-pod/${shipmentInfoPODEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ shipmentInfoPOD }: IRootState) => ({
  shipmentInfoPODEntity: shipmentInfoPOD.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentInfoPODDetail);
