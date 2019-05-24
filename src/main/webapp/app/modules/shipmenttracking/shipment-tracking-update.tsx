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
import { getEntity, updateEntity, createEntity, reset, resetForNew, autoFillNewTracking } from './shipment-tracking.reducer';
import { IShipmentTracking } from 'app/shared/model/shipment-tracking.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import moment from 'moment';
export interface IShipmentTrackingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IShipmentTrackingUpdateState {
  isNew: boolean;
  shipmentInfoId: string;
  isDelivered: boolean;
}

export class ShipmentTrackingUpdate extends React.Component<IShipmentTrackingUpdateProps, IShipmentTrackingUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      shipmentInfoId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id,
      isDelivered: false
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.resetForNew();
      this.props.autoFillNewTracking(this.props.searchValue, this.props.vendorId);
    } else {
      this.props.getEntity(this.props.match.params.id);
      if (this.props.shipmentTrackingEntity.isDelivered) {
        this.setState({ isDelivered: true });
      }
    }

    //this.props.getShipmentInfos();
  }

  saveEntity = (event, errors, values) => {
    values.trackingDate = convertDateTimeToServer(values.trackingDate);

    if (errors.length === 0) {
      const { shipmentTrackingEntity, shipmentTrackingEntities } = this.props;
      const entity = {
        ...shipmentTrackingEntity,
        ...values
      };

      if (this.state.isNew) {
        entity.shipmentInfoId = shipmentTrackingEntities[0].shipmentInfoId;
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      //this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/shipment-tracking');
  };

  onChangeDelivered = event => {
    //alert(event);
    //alert(event.target.checked);
    this.setState({ isDelivered: event.target.checked });
  };

  render() {
    const { shipmentTrackingEntity, loading, updating } = this.props;
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
              <AvForm model={shipmentTrackingEntity} onSubmit={this.saveEntity}>
                {/*<AvForm model={isNew ? { trackingDate: moment().format('YYYY-MM-DDTHH:mm') } : shipmentTrackingEntity} onSubmit={this.saveEntity}></AvForm>*/}
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
                    //value={isNew ? null : convertDateTimeFromServer(this.props.shipmentTrackingEntity.trackingDate)}
                    value={convertDateTimeFromServer(this.props.shipmentTrackingEntity.trackingDate)}
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
                  <Label id="isInTransitLabel" check className="cargotracker-label">
                    <AvInput id="shipment-info-isInTransit" type="checkbox" className="form-control" name="isInTransit" />
                    <Translate contentKey="cargotrackerApp.shipmentInfo.isInTransit">Is Transit</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="isReachedNearestHubLabel" check className="cargotracker-label">
                    <AvInput id="shipment-info-isReachedNearestHub" type="checkbox" className="form-control" name="isReachedNearestHub" />
                    <Translate contentKey="cargotrackerApp.shipmentInfo.isReachedNearestHub">Is ReachedNearestHub</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="isOutForDeliveryHubLabel" check className="cargotracker-label">
                    <AvInput id="shipment-info-isOutForDelivery" type="checkbox" className="form-control" name="isOutForDelivery" />
                    <Translate contentKey="cargotrackerApp.shipmentInfo.isOutForDelivery">Is ReachedNearestHub</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="isDeliveredLabel" check className="cargotracker-label">
                    <AvInput
                      id="shipment-info-isDelivered"
                      type="checkbox"
                      className="form-control"
                      name="isDelivered"
                      onChange={this.onChangeDelivered}
                    />
                    <Translate contentKey="cargotrackerApp.shipmentInfo.isDelivered">Is Delivered</Translate>
                  </Label>
                </AvGroup>
                {this.state.isDelivered && (
                  <>
                    <AvGroup>
                      <Label id="receivedByLabel" for="receivedBy">
                        <Translate contentKey="cargotrackerApp.shipmentInfo.receivedBy">Received By</Translate>
                      </Label>
                      <AvField
                        id="shipment-tracking-receivedBy"
                        type="text"
                        name="receivedBy"
                        validate={{
                          required: { value: true, errorMessage: translate('entity.validation.required') }
                        }}
                      />
                    </AvGroup>
                    <AvGroup>
                      <Label id="relationshipLabel" for="relationship">
                        <Translate contentKey="cargotrackerApp.shipmentInfo.relationShip">Received By</Translate>
                      </Label>
                      <AvField
                        id="shipment-tracking-relationship"
                        type="text"
                        name="relationShip"
                        validate={{
                          required: { value: true, errorMessage: translate('entity.validation.required') }
                        }}
                      />
                    </AvGroup>
                  </>
                )}
                {/*<AvGroup>
                  <Label for="shipmentInfo.id">
                    <Translate contentKey="cargotrackerApp.shipmentTracking.shipmentInfo">Shipment Info</Translate>
                  </Label>
                  <AvInput id="shipment-tracking-shipmentInfo" type="select" className="form-control" name="shipmentInfoId">
                    <option value="" key="0" />
                    {shipmentInfos
                      ? shipmentInfos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>*/}
                <Button tag={Link} id="cancel-save" to="/shipment-tracking" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
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
  //shipmentInfos: storeState.shipmentInfo.entities,
  shipmentTrackingEntity: storeState.shipmentTrackingSolo.entity,
  shipmentTrackingEntities: storeState.shipmentTrackingSolo.entities,
  loading: storeState.shipmentTrackingSolo.loading,
  updating: storeState.shipmentTrackingSolo.updating,
  updateSuccess: storeState.shipmentTrackingSolo.updateSuccess,
  vendorId: storeState.authentication.account.vendorId,
  searchValue: storeState.shipmentTrackingSolo.search
});

const mapDispatchToProps = {
  //getShipmentInfos,
  getEntity,
  updateEntity,
  createEntity,
  reset,
  resetForNew,
  autoFillNewTracking
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentTrackingUpdate);
