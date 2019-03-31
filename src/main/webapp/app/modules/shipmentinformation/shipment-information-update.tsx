import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICarrierDetails } from 'app/shared/model/carrier-details.model';
import { getEntities as getCarrierDetails } from 'app/entities/carrier-details/carrier-details.reducer';
import { IShipmentType } from 'app/shared/model/shipment-type.model';
import { getEntities as getShipmentTypes } from 'app/entities/shipment-type/shipment-type.reducer';
import { IShipmentMode } from 'app/shared/model/shipment-mode.model';
import { getEntities as getShipmentModes } from 'app/entities/shipment-mode/shipment-mode.reducer';
import { IPaymentMode } from 'app/shared/model/payment-mode.model';
import { getEntities as getPaymentModes } from 'app/entities/payment-mode/payment-mode.reducer';
import { ITrackingStatus } from 'app/shared/model/tracking-status.model';
import { getEntities as getTrackingStatuses } from 'app/entities/tracking-status/tracking-status.reducer';
import { IVendor } from 'app/shared/model/vendor.model';
import { getEntities as getVendors } from 'app/entities/vendor/vendor.reducer';
import { IState } from 'app/shared/model/state.model';
import { getEntities as getStates } from 'app/entities/state/state.reducer';
import { getEntity, updateEntity, createEntity, reset } from './shipment-information.reducer';
import { IShipmentInfo } from 'app/shared/model/shipment-info.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IShipmentInformationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IShipmentInformationUpdateState {
  isNew: boolean;
  carrierDetailsId: string;
  shipmentTypeId: string;
  shipmentModeId: string;
  paymentModeId: string;
  trackingStatusId: string;
  vendorId: string;
  originId: string;
  destinationId: string;
}

export class ShipmentInformationUpdate extends React.Component<IShipmentInformationUpdateProps, IShipmentInformationUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      carrierDetailsId: '0',
      shipmentTypeId: '0',
      shipmentModeId: '0',
      paymentModeId: '0',
      trackingStatusId: '0',
      vendorId: '0',
      originId: '0',
      destinationId: '0',
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

    this.props.getCarrierDetails();
    this.props.getShipmentTypes();
    this.props.getShipmentModes();
    this.props.getPaymentModes();
    this.props.getTrackingStatuses();
    this.props.getVendors();
    this.props.getStates();
  }

  saveEntity = (event, errors, values) => {
    values.bookingDate = convertDateTimeToServer(values.bookingDate);
    values.expectedDeliveryDate = convertDateTimeToServer(values.expectedDeliveryDate);
    values.deliveredDate = convertDateTimeToServer(values.deliveredDate);

    if (errors.length === 0) {
      const { shipmentInfoEntity } = this.props;
      const entity = {
        ...shipmentInfoEntity,
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
    this.props.history.push('/entity/shipment-info');
  };

  render() {
    const {
      shipmentInfoEntity,
      carrierDetails,
      shipmentTypes,
      shipmentModes,
      paymentModes,
      trackingStatuses,
      vendors,
      states,
      loading,
      updating
    } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="cargotrackerApp.shipmentInfo.home.createOrEditLabel">
              <Translate contentKey="cargotrackerApp.shipmentInfo.home.createOrEditLabel">Create or edit a ShipmentInfo</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : shipmentInfoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="shipment-info-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="consignmentNoLabel" for="consignmentNo">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.consignmentNo">Consignment No</Translate>
                  </Label>
                  <AvField
                    id="shipment-info-consignmentNo"
                    type="text"
                    name="consignmentNo"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="bookingDateLabel" for="bookingDate">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.bookingDate">Booking Date</Translate>
                  </Label>
                  <AvInput
                    id="shipment-info-bookingDate"
                    type="datetime-local"
                    className="form-control"
                    name="bookingDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.shipmentInfoEntity.bookingDate)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="expectedDeliveryDateLabel" for="expectedDeliveryDate">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.expectedDeliveryDate">Expected Delivery Date</Translate>
                  </Label>
                  <AvInput
                    id="shipment-info-expectedDeliveryDate"
                    type="datetime-local"
                    className="form-control"
                    name="expectedDeliveryDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.shipmentInfoEntity.expectedDeliveryDate)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="actualWeightLabel" for="actualWeight">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.actualWeight">Actual Weight</Translate>
                  </Label>
                  <AvField id="shipment-info-actualWeight" type="text" name="actualWeight" />
                </AvGroup>
                <AvGroup>
                  <Label id="volumetricWeightLabel" for="volumetricWeight">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.volumetricWeight">Volumetric Weight</Translate>
                  </Label>
                  <AvField id="shipment-info-volumetricWeight" type="text" name="volumetricWeight" />
                </AvGroup>
                <AvGroup>
                  <Label id="lengthLabel" for="length">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.length">Length</Translate>
                  </Label>
                  <AvField id="shipment-info-length" type="text" name="length" />
                </AvGroup>
                <AvGroup>
                  <Label id="widthLabel" for="width">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.width">Width</Translate>
                  </Label>
                  <AvField id="shipment-info-width" type="text" name="width" />
                </AvGroup>
                <AvGroup>
                  <Label id="heightLabel" for="height">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.height">Height</Translate>
                  </Label>
                  <AvField id="shipment-info-height" type="text" name="height" />
                </AvGroup>
                <AvGroup>
                  <Label id="quantityLabel" for="quantity">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.quantity">Quantity</Translate>
                  </Label>
                  <AvField id="shipment-info-quantity" type="string" className="form-control" name="quantity" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalFrightLabel" for="totalFright">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.totalFright">Total Fright</Translate>
                  </Label>
                  <AvField id="shipment-info-totalFright" type="text" name="totalFright" />
                </AvGroup>
                <AvGroup>
                  <Label id="packageDesciptionLabel" for="packageDesciption">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.packageDesciption">Package Desciption</Translate>
                  </Label>
                  <AvField id="shipment-info-packageDesciption" type="text" name="packageDesciption" />
                </AvGroup>
                <AvGroup>
                  <Label id="isThirdPartyLabel" check>
                    <AvInput id="shipment-info-isThirdParty" type="checkbox" className="form-control" name="isThirdParty" />
                    <Translate contentKey="cargotrackerApp.shipmentInfo.isThirdParty">Is Third Party</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="carrierRefNoLabel" for="carrierRefNo">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.carrierRefNo">Carrier Ref No</Translate>
                  </Label>
                  <AvField id="shipment-info-carrierRefNo" type="text" name="carrierRefNo" />
                </AvGroup>
                <AvGroup>
                  <Label id="deliveredDateLabel" for="deliveredDate">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.deliveredDate">Delivered Date</Translate>
                  </Label>
                  <AvInput
                    id="shipment-info-deliveredDate"
                    type="datetime-local"
                    className="form-control"
                    name="deliveredDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.shipmentInfoEntity.deliveredDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="receivedByLabel" for="receivedBy">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.receivedBy">Received By</Translate>
                  </Label>
                  <AvField id="shipment-info-receivedBy" type="text" name="receivedBy" />
                </AvGroup>
                <AvGroup>
                  <Label id="relationShipLabel" for="relationShip">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.relationShip">Relation Ship</Translate>
                  </Label>
                  <AvField id="shipment-info-relationShip" type="text" name="relationShip" />
                </AvGroup>
                <AvGroup>
                  <Label for="carrierDetails.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.carrierDetails">Carrier Details</Translate>
                  </Label>
                  <AvInput id="shipment-info-carrierDetails" type="select" className="form-control" name="carrierDetailsId">
                    <option value="" key="0" />
                    {carrierDetails
                      ? carrierDetails.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.value}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="shipmentType.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentType">Shipment Type</Translate>
                  </Label>
                  <AvInput id="shipment-info-shipmentType" type="select" className="form-control" name="shipmentTypeId">
                    <option value="" key="0" />
                    {shipmentTypes
                      ? shipmentTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.value}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="shipmentMode.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentMode">Shipment Mode</Translate>
                  </Label>
                  <AvInput id="shipment-info-shipmentMode" type="select" className="form-control" name="shipmentModeId">
                    <option value="" key="0" />
                    {shipmentModes
                      ? shipmentModes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.value}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="paymentMode.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.paymentMode">Payment Mode</Translate>
                  </Label>
                  <AvInput id="shipment-info-paymentMode" type="select" className="form-control" name="paymentModeId">
                    <option value="" key="0" />
                    {paymentModes
                      ? paymentModes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.value}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="trackingStatus.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.trackingStatus">Tracking Status</Translate>
                  </Label>
                  <AvInput id="shipment-info-trackingStatus" type="select" className="form-control" name="trackingStatusId">
                    <option value="" key="0" />
                    {trackingStatuses
                      ? trackingStatuses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.value}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="vendor.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.vendor">Vendor</Translate>
                  </Label>
                  <AvInput id="shipment-info-vendor" type="select" className="form-control" name="vendorId">
                    <option value="" key="0" />
                    {vendors
                      ? vendors.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.vendorname}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="origin.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.origin">Origin</Translate>
                  </Label>
                  <AvInput id="shipment-info-origin" type="select" className="form-control" name="originId">
                    <option value="" key="0" />
                    {states
                      ? states.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.stateName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="destination.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.destination">Destination</Translate>
                  </Label>
                  <AvInput id="shipment-info-destination" type="select" className="form-control" name="destinationId">
                    <option value="" key="0" />
                    {states
                      ? states.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.stateName}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/shipment-info" replace color="info">
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
  carrierDetails: storeState.carrierDetails.entities,
  shipmentTypes: storeState.shipmentType.entities,
  shipmentModes: storeState.shipmentMode.entities,
  paymentModes: storeState.paymentMode.entities,
  trackingStatuses: storeState.trackingStatus.entities,
  vendors: storeState.vendor.entities,
  states: storeState.state.entities,
  shipmentInfoEntity: storeState.shipmentInfo.entity,
  loading: storeState.shipmentInfo.loading,
  updating: storeState.shipmentInfo.updating,
  updateSuccess: storeState.shipmentInfo.updateSuccess
});

const mapDispatchToProps = {
  getCarrierDetails,
  getShipmentTypes,
  getShipmentModes,
  getPaymentModes,
  getTrackingStatuses,
  getVendors,
  getStates,
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
)(ShipmentInformationUpdate);
