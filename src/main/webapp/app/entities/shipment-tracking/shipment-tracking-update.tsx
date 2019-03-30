import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IShipmentInfo } from 'app/shared/model/shipment-info.model';
import { getEntities as getShipmentInfos } from 'app/entities/shipment-info/shipment-info.reducer';
import { getEntity, updateEntity, createEntity, reset } from './shipment-tracking.reducer';
import { IShipmentTracking } from 'app/shared/model/shipment-tracking.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IShipmentTrackingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IShipmentTrackingUpdateState {
  isNew: boolean;
  shipmentInfoId: string;
}

export class ShipmentTrackingUpdate extends React.Component<IShipmentTrackingUpdateProps, IShipmentTrackingUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      shipmentInfoId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getShipmentInfos();
  }

  saveEntity = (event, errors, values) => {
    values.trackingDate = convertDateTimeToServer(values.trackingDate);

    if (errors.length === 0) {
      const { shipmentTrackingEntity } = this.props;
      const entity = {
        ...shipmentTrackingEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/shipment-tracking');
  };

  render() {
    const { shipmentTrackingEntity, shipmentInfos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="cargotrackerApp.shipmentTracking.home.createOrEditLabel">
              <Translate contentKey="cargotrackerApp.shipmentTracking.home.createOrEditLabel">Create or edit a ShipmentTracking</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : shipmentTrackingEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="shipment-tracking-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="trackingDateLabel" for="trackingDate">
                    <Translate contentKey="cargotrackerApp.shipmentTracking.trackingDate">Tracking Date</Translate>
                  </Label>
                  <AvInput
                    id="shipment-tracking-trackingDate"
                    type="datetime-local"
                    className="form-control"
                    name="trackingDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.shipmentTrackingEntity.trackingDate)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="placeLabel" for="place">
                    <Translate contentKey="cargotrackerApp.shipmentTracking.place">Place</Translate>
                  </Label>
                  <AvField
                    id="shipment-tracking-place"
                    type="text"
                    name="place"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" for="status">
                    <Translate contentKey="cargotrackerApp.shipmentTracking.status">Status</Translate>
                  </Label>
                  <AvField
                    id="shipment-tracking-status"
                    type="text"
                    name="status"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="shipmentInfo.id">
                    <Translate contentKey="cargotrackerApp.shipmentTracking.shipmentInfo">Shipment Info</Translate>
                  </Label>
                  <AvInput id="shipment-tracking-shipmentInfo" type="select" className="form-control" name="shipmentInfo.id">
                    <option value="" key="0" />
                    {shipmentInfos
                      ? shipmentInfos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/shipment-tracking" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  shipmentInfos: storeState.shipmentInfo.entities,
  shipmentTrackingEntity: storeState.shipmentTracking.entity,
  loading: storeState.shipmentTracking.loading,
  updating: storeState.shipmentTracking.updating,
  updateSuccess: storeState.shipmentTracking.updateSuccess
});

const mapDispatchToProps = {
  getShipmentInfos,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentTrackingUpdate);
