import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVendor } from 'app/shared/model/vendor.model';
import { getEntities as getVendors } from 'app/entities/vendor/vendor.reducer';
import { IState } from 'app/shared/model/state.model';
import { getEntities as getStates } from 'app/entities/state/state.reducer';
import { getEntity, updateEntity, createEntity, reset } from './shipment-info.reducer';
import { IShipmentInfo } from 'app/shared/model/shipment-info.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IShipmentInfoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IShipmentInfoUpdateState {
  isNew: boolean;
  vendorId: string;
  originId: string;
  destinationId: string;
}

export class ShipmentInfoUpdate extends React.Component<IShipmentInfoUpdateProps, IShipmentInfoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getVendors();
    this.props.getStates();
  }

  saveEntity = (event, errors, values) => {
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
    const { shipmentInfoEntity, vendors, states, loading, updating } = this.props;
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
                  <Label id="isThirdPartyLabel" check>
                    <AvInput id="shipment-info-isThirdParty" type="checkbox" className="form-control" name="isThirdParty" />
                    <Translate contentKey="cargotrackerApp.shipmentInfo.isThirdParty">Is Third Party</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="carrierLabel">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.carrier">Carrier</Translate>
                  </Label>
                  <AvInput
                    id="shipment-info-carrier"
                    type="select"
                    className="form-control"
                    name="carrier"
                    value={(!isNew && shipmentInfoEntity.carrier) || 'DHL'}
                  >
                    <option value="DHL">
                      <Translate contentKey="cargotrackerApp.CARRIER.DHL" />
                    </option>
                    <option value="FEDEX">
                      <Translate contentKey="cargotrackerApp.CARRIER.FEDEX" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="courierLabel" for="courier">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.courier">Courier</Translate>
                  </Label>
                  <AvField id="shipment-info-courier" type="text" name="courier" />
                </AvGroup>
                <AvGroup>
                  <Label id="carrierRefNoLabel" for="carrierRefNo">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.carrierRefNo">Carrier Ref No</Translate>
                  </Label>
                  <AvField id="shipment-info-carrierRefNo" type="text" name="carrierRefNo" />
                </AvGroup>
                <AvGroup>
                  <Label id="shipmentTypeLabel">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentType">Shipment Type</Translate>
                  </Label>
                  <AvInput
                    id="shipment-info-shipmentType"
                    type="select"
                    className="form-control"
                    name="shipmentType"
                    value={(!isNew && shipmentInfoEntity.shipmentType) || 'DOCUMENT'}
                  >
                    <option value="DOCUMENT">
                      <Translate contentKey="cargotrackerApp.ShipmentType.DOCUMENT" />
                    </option>
                    <option value="PARCEL">
                      <Translate contentKey="cargotrackerApp.ShipmentType.PARCEL" />
                    </option>
                    <option value="PACKAGE">
                      <Translate contentKey="cargotrackerApp.ShipmentType.PACKAGE" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="shipmentModeLabel">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentMode">Shipment Mode</Translate>
                  </Label>
                  <AvInput
                    id="shipment-info-shipmentMode"
                    type="select"
                    className="form-control"
                    name="shipmentMode"
                    value={(!isNew && shipmentInfoEntity.shipmentMode) || 'AIR'}
                  >
                    <option value="AIR">
                      <Translate contentKey="cargotrackerApp.ShipmentMode.AIR" />
                    </option>
                    <option value="ROAD">
                      <Translate contentKey="cargotrackerApp.ShipmentMode.ROAD" />
                    </option>
                    <option value="SEA">
                      <Translate contentKey="cargotrackerApp.ShipmentMode.SEA" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="originLabel" for="origin">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.origin">Origin</Translate>
                  </Label>
                  <AvField
                    id="shipment-info-origin"
                    type="text"
                    name="origin"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="destinationLabel" for="destination">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.destination">Destination</Translate>
                  </Label>
                  <AvField
                    id="shipment-info-destination"
                    type="text"
                    name="destination"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="pickupDateLabel" for="pickupDate">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.pickupDate">Pickup Date</Translate>
                  </Label>
                  <AvField
                    id="shipment-info-pickupDate"
                    type="date"
                    className="form-control"
                    name="pickupDate"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="expectedDeliveryDateLabel" for="expectedDeliveryDate">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.expectedDeliveryDate">Expected Delivery Date</Translate>
                  </Label>
                  <AvField
                    id="shipment-info-expectedDeliveryDate"
                    type="date"
                    className="form-control"
                    name="expectedDeliveryDate"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="weightLabel" for="weight">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.weight">Weight</Translate>
                  </Label>
                  <AvField id="shipment-info-weight" type="text" name="weight" />
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
                  <Label id="paymentModeLabel">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.paymentMode">Payment Mode</Translate>
                  </Label>
                  <AvInput
                    id="shipment-info-paymentMode"
                    type="select"
                    className="form-control"
                    name="paymentMode"
                    value={(!isNew && shipmentInfoEntity.paymentMode) || 'TOPAY'}
                  >
                    <option value="TOPAY">
                      <Translate contentKey="cargotrackerApp.PaymentMode.TOPAY" />
                    </option>
                    <option value="PAID">
                      <Translate contentKey="cargotrackerApp.PaymentMode.PAID" />
                    </option>
                    <option value="CREDIT">
                      <Translate contentKey="cargotrackerApp.PaymentMode.CREDIT" />
                    </option>
                    <option value="CASH">
                      <Translate contentKey="cargotrackerApp.PaymentMode.CASH" />
                    </option>
                    <option value="CHEQUE">
                      <Translate contentKey="cargotrackerApp.PaymentMode.CHEQUE" />
                    </option>
                    <option value="ACCOUNT">
                      <Translate contentKey="cargotrackerApp.PaymentMode.ACCOUNT" />
                    </option>
                    <option value="UPI">
                      <Translate contentKey="cargotrackerApp.PaymentMode.UPI" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.status">Status</Translate>
                  </Label>
                  <AvInput
                    id="shipment-info-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && shipmentInfoEntity.status) || 'INTRANSIENT'}
                  >
                    <option value="INTRANSIENT">
                      <Translate contentKey="cargotrackerApp.Status.INTRANSIENT" />
                    </option>
                    <option value="PENDING">
                      <Translate contentKey="cargotrackerApp.Status.PENDING" />
                    </option>
                    <option value="DELIVERED">
                      <Translate contentKey="cargotrackerApp.Status.DELIVERED" />
                    </option>
                    <option value="DELAYED">
                      <Translate contentKey="cargotrackerApp.Status.DELAYED" />
                    </option>
                    <option value="ONHOLD">
                      <Translate contentKey="cargotrackerApp.Status.ONHOLD" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="commentsLabel" for="comments">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.comments">Comments</Translate>
                  </Label>
                  <AvField id="shipment-info-comments" type="text" name="comments" />
                </AvGroup>
                <AvGroup>
                  <Label for="vendor.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.vendor">Vendor</Translate>
                  </Label>
                  <AvInput id="shipment-info-vendor" type="select" className="form-control" name="vendor.id">
                    <option value="" key="0" />
                    {vendors
                      ? vendors.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="origin.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.origin">Origin</Translate>
                  </Label>
                  <AvInput id="shipment-info-origin" type="select" className="form-control" name="origin.id">
                    <option value="" key="0" />
                    {states
                      ? states.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="destination.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfo.destination">Destination</Translate>
                  </Label>
                  <AvInput id="shipment-info-destination" type="select" className="form-control" name="destination.id">
                    <option value="" key="0" />
                    {states
                      ? states.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/shipment-info" replace color="info">
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
  vendors: storeState.vendor.entities,
  states: storeState.state.entities,
  shipmentInfoEntity: storeState.shipmentInfo.entity,
  loading: storeState.shipmentInfo.loading,
  updating: storeState.shipmentInfo.updating,
  updateSuccess: storeState.shipmentInfo.updateSuccess
});

const mapDispatchToProps = {
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
)(ShipmentInfoUpdate);
