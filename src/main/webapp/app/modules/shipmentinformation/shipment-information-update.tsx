import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, FormGroup, Card, CardBody, CardTitle } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICarrierDetails } from 'app/shared/model/carrier-details.model';
import { getEntities as getCarrierDetails, getCarierDetailsByVendorId } from 'app/entities/carrier-details/carrier-details.reducer';
import { IShipmentType } from 'app/shared/model/shipment-type.model';
import { getEntities as getShipmentTypes, getShipmentTypesByVendorId } from 'app/entities/shipment-type/shipment-type.reducer';
import { IShipmentMode } from 'app/shared/model/shipment-mode.model';
import { getEntities as getShipmentModes, getShipmentModesByVendorId } from 'app/entities/shipment-mode/shipment-mode.reducer';
import { IPaymentMode } from 'app/shared/model/payment-mode.model';
import { getEntities as getPaymentModes, getPaymentModesByVendorId } from 'app/entities/payment-mode/payment-mode.reducer';
import { ITrackingStatus } from 'app/shared/model/tracking-status.model';
import { getEntities as getTrackingStatuses, getTrackingStatusByVendorId } from 'app/entities/tracking-status/tracking-status.reducer';
import { IVendor } from 'app/shared/model/vendor.model';
import { getEntities as getVendors } from 'app/entities/vendor/vendor.reducer';
import { IState } from 'app/shared/model/state.model';
import { getEntities as getStates } from 'app/entities/state/state.reducer';
import { getEntity, updateEntity, createEntity, reset } from './shipment-information.reducer';
import { IShipmentInfo } from 'app/shared/model/shipment-info.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import moment from 'moment';

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

    const { vendorId, carrierDetails, shipmentTypes, shipmentModes, paymentModes, trackingStatuses, states } = this.props;

    if (vendorId !== undefined && vendorId !== null) {
      if (carrierDetails !== undefined && carrierDetails.length === 0) {
        this.props.getCarierDetailsByVendorId(this.props.vendorId);
      }
      if (shipmentTypes !== undefined && shipmentTypes.length === 0) {
        this.props.getShipmentTypesByVendorId(this.props.vendorId);
      }
      if (shipmentModes !== undefined && shipmentModes.length === 0) {
        this.props.getShipmentModesByVendorId(this.props.vendorId);
      }
      if (paymentModes !== undefined && paymentModes.length === 0) {
        this.props.getPaymentModesByVendorId(this.props.vendorId);
      }
      if (trackingStatuses !== undefined && trackingStatuses.length === 0) {
        this.props.getTrackingStatusByVendorId(this.props.vendorId);
      }
      if (states !== undefined && states.length === 0) {
        this.props.getStates();
      }
    }
  }

  saveEntity = (event, errors, values) => {
    values.bookingDate = convertDateTimeToServer(values.bookingDate);
    values.expectedDeliveryDate = convertDateTimeToServer(values.expectedDeliveryDate);
    values.deliveredDate = convertDateTimeToServer(values.deliveredDate);

    if (errors.length === 0) {
      const { shipmentInfoEntity, vendorId } = this.props;
      // console.log(JSON.stringify(shipmentInfoEntity, null, 2));
      // console.log(JSON.stringify(values, null, 2));
      shipmentInfoEntity.vendorId = vendorId;
      const entity = {
        ...shipmentInfoEntity,
        ...values
      };
      // console.log(JSON.stringify(entity, null, 2));
      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/shipment-information');
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
          <Col md="12">
            <h2 id="cargotrackerApp.shipmentInfo.home.createOrEditLabel" className="cargotracker-header cargotracker-textalign-center">
              {!isNew ? (
                <Translate contentKey="cargotrackerApp.shipmentInfo.home.edit">Edit a ShipmentInfo</Translate>
              ) : (
                <>
                  <Translate contentKey="cargotrackerApp.shipmentInfo.home.create">Create a ShipmentInfo</Translate>
                </>
              )}
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="12">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? { bookingDate: moment().format('YYYY-MM-DDTHH:mm') } : shipmentInfoEntity} onSubmit={this.saveEntity}>
                {/*!isNew ? (
                    <AvGroup>
                      <Label for="id">
                        <Translate contentKey="global.field.id">ID</Translate>
                      </Label>
                      <AvInput id="shipment-info-id" type="text" className="form-control" name="id" required readOnly />
                    </AvGroup>
                  ) : null*/}
                <Row>
                  <Col sm={6}>
                    <Card>
                      <CardTitle>Consignor</CardTitle>
                      <CardBody>
                        <Row>
                          <Col sm={6}>
                            <FormGroup row>
                              <Col sm={4}>
                                <Label id="nameLabel" for="name" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.name">Name</Translate> :
                                  <span className="cargotracker-mandate-field">* </span>
                                </Label>
                              </Col>
                              <Col sm={8}>
                                <AvField
                                  id="shiper-receiver-info-name"
                                  type="text"
                                  name="shipperInfo.name"
                                  validate={{
                                    required: { value: true, errorMessage: translate('entity.validation.required') }
                                  }}
                                />
                              </Col>
                            </FormGroup>
                          </Col>
                          <Col sm={6}>
                            <FormGroup row>
                              <Col sm={4}>
                                <Label id="phoneNoLabel" for="phoneNo" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.phoneNo">Phone No</Translate> :
                                  <span className="cargotracker-mandate-field">* </span>
                                </Label>
                              </Col>
                              <Col sm={8}>
                                <AvField
                                  id="shiper-receiver-info-phoneNo"
                                  type="text"
                                  name="shipperInfo.phoneNo"
                                  validate={{
                                    required: { value: true, errorMessage: translate('entity.validation.required') }
                                  }}
                                />
                              </Col>
                            </FormGroup>
                          </Col>
                        </Row>

                        <Row>
                          <Col sm={12}>
                            <FormGroup row>
                              <Col sm={2}>
                                <Label id="addressLabel" for="address" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.address">Address</Translate> :
                                  <span className="cargotracker-mandate-field">* </span>
                                </Label>
                              </Col>
                              <Col sm={10}>
                                <AvField
                                  id="shiper-receiver-info-address"
                                  type="textarea"
                                  name="shipperInfo.address"
                                  validate={{
                                    required: { value: true, errorMessage: translate('entity.validation.required') }
                                  }}
                                />
                              </Col>
                            </FormGroup>
                          </Col>
                        </Row>

                        <Row>
                          <Col sm={6}>
                            <FormGroup row>
                              <Col sm={4}>
                                <Label id="cityLabel" for="city" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.city">City</Translate>
                                </Label>
                              </Col>
                              <Col sm={8}>
                                <AvField id="shiper-receiver-info-city" type="text" name="shipperInfo.city" />
                              </Col>
                            </FormGroup>
                          </Col>
                          <Col sm={6}>
                            <FormGroup row>
                              <Col sm={4}>
                                <Label id="pincodeLabel" for="pincode" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.pincode">Pincode</Translate> :
                                  <span className="cargotracker-mandate-field">* </span>
                                </Label>
                              </Col>
                              <Col sm={8}>
                                <AvField
                                  id="shiper-receiver-info-pincode"
                                  type="text"
                                  name="shipperInfo.pincode"
                                  validate={{
                                    required: { value: true, errorMessage: translate('entity.validation.required') }
                                  }}
                                />
                              </Col>
                            </FormGroup>
                          </Col>
                        </Row>

                        <Row>
                          <Col sm={8}>
                            <FormGroup row>
                              <Col sm={3}>
                                <Label id="emailIdLabel" for="emailId" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.emailId">Email Id</Translate>
                                </Label>
                              </Col>
                              <Col sm={9}>
                                <AvField id="shiper-receiver-info-emailId" type="text" name="shipperInfo.emailId" />
                              </Col>
                            </FormGroup>
                          </Col>
                        </Row>
                      </CardBody>
                    </Card>
                  </Col>
                  <Col sm={6}>
                    <Card>
                      <CardTitle>Consignee</CardTitle>
                      <CardBody>
                        <Row>
                          <Col sm={6}>
                            <FormGroup row>
                              <Col sm={4}>
                                <Label id="nameLabel" for="name" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.name">Name</Translate> :
                                  <span className="cargotracker-mandate-field">* </span>
                                </Label>
                              </Col>
                              <Col sm={8}>
                                <AvField
                                  id="shiper-receiver-info-name"
                                  type="text"
                                  name="receiverInfo.name"
                                  validate={{
                                    required: { value: true, errorMessage: translate('entity.validation.required') }
                                  }}
                                />
                              </Col>
                            </FormGroup>
                          </Col>
                          <Col sm={6}>
                            <FormGroup row>
                              <Col sm={4}>
                                <Label id="phoneNoLabel" for="phoneNo" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.phoneNo">Phone No</Translate> :
                                  <span className="cargotracker-mandate-field">* </span>
                                </Label>
                              </Col>
                              <Col sm={8}>
                                <AvField
                                  id="shiper-receiver-info-phoneNo"
                                  type="text"
                                  name="receiverInfo.phoneNo"
                                  validate={{
                                    required: { value: true, errorMessage: translate('entity.validation.required') }
                                  }}
                                />
                              </Col>
                            </FormGroup>
                          </Col>
                        </Row>

                        <Row>
                          <Col sm={12}>
                            <FormGroup row>
                              <Col sm={2}>
                                <Label id="addressLabel" for="address" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.address">Address</Translate> :
                                  <span className="cargotracker-mandate-field">* </span>
                                </Label>
                              </Col>
                              <Col sm={10}>
                                <AvField
                                  id="shiper-receiver-info-address"
                                  type="textarea"
                                  name="receiverInfo.address"
                                  validate={{
                                    required: { value: true, errorMessage: translate('entity.validation.required') }
                                  }}
                                />
                              </Col>
                            </FormGroup>
                          </Col>
                        </Row>

                        <Row>
                          <Col sm={6}>
                            <FormGroup row>
                              <Col sm={4}>
                                <Label id="cityLabel" for="city" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.city">City</Translate>
                                </Label>
                              </Col>
                              <Col sm={8}>
                                <AvField id="shiper-receiver-info-city" type="text" name="receiverInfo.city" />
                              </Col>
                            </FormGroup>
                          </Col>
                          <Col sm={6}>
                            <FormGroup row>
                              <Col sm={4}>
                                <Label id="pincodeLabel" for="pincode" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.pincode">Pincode</Translate> :
                                  <span className="cargotracker-mandate-field">* </span>
                                </Label>
                              </Col>
                              <Col sm={8}>
                                <AvField
                                  id="shiper-receiver-info-pincode"
                                  type="text"
                                  name="receiverInfo.pincode"
                                  validate={{
                                    required: { value: true, errorMessage: translate('entity.validation.required') }
                                  }}
                                />
                              </Col>
                            </FormGroup>
                          </Col>
                        </Row>

                        <Row>
                          <Col sm={8}>
                            <FormGroup row>
                              <Col sm={3}>
                                <Label id="emailIdLabel" for="emailId" className="cargotracker-label">
                                  <Translate contentKey="cargotrackerApp.shiperReceiverInfo.emailId">Email Id</Translate>
                                </Label>
                              </Col>
                              <Col sm={9}>
                                <AvField id="shiper-receiver-info-emailId" type="text" name="receiverInfo.emailId" />
                              </Col>
                            </FormGroup>
                          </Col>
                        </Row>
                      </CardBody>
                    </Card>
                  </Col>
                </Row>
                <Card>
                  <CardTitle>Shipment Details</CardTitle>
                  <CardBody>
                    <Row>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label id="consignmentNoLabel" for="consignmentNo" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.consignmentNo">Consignment No : </Translate> :
                              <span className="cargotracker-mandate-field">* </span>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvField
                              id="shipment-info-consignmentNo"
                              type="text"
                              name="consignmentNo"
                              validate={{
                                required: { value: true, errorMessage: translate('entity.validation.required') }
                              }}
                            />
                          </Col>
                        </FormGroup>
                      </Col>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label id="bookingDateLabel" for="bookingDate" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.bookingDate">Booking Date :</Translate> :
                              <span className="cargotracker-mandate-field">* </span>
                            </Label>
                          </Col>
                          <Col sm={7}>
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
                          </Col>
                        </FormGroup>
                      </Col>

                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label id="expectedDeliveryDateLabel" for="expectedDeliveryDate" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.expectedDeliveryDate">Expected Delivery Date</Translate> :
                              <span className="cargotracker-mandate-field">* </span>
                            </Label>
                          </Col>
                          <Col sm={7}>
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
                          </Col>
                        </FormGroup>
                      </Col>
                    </Row>

                    <Row>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label for="shipmentType.id" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentType">Shipment Type</Translate> :
                              <span className="cargotracker-mandate-field">* </span>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvInput
                              id="shipment-info-shipmentType"
                              type="select"
                              className="form-control"
                              name="shipmentTypeId"
                              validate={{
                                required: { value: true, errorMessage: translate('entity.validation.required') }
                              }}
                            >
                              <option value={-1} key={-1}>
                                --Select--
                              </option>
                              {shipmentTypes
                                ? shipmentTypes.map(otherEntity => (
                                    <option value={otherEntity.id} key={otherEntity.id}>
                                      {otherEntity.value}
                                    </option>
                                  ))
                                : null}
                            </AvInput>
                          </Col>
                        </FormGroup>
                      </Col>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label for="shipmentMode.id" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.shipmentMode">Shipment Mode</Translate> :
                              <span className="cargotracker-mandate-field">* </span>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvInput
                              id="shipment-info-shipmentMode"
                              type="select"
                              className="form-control"
                              name="shipmentModeId"
                              validate={{
                                required: { value: true, errorMessage: translate('entity.validation.required') }
                              }}
                            >
                              <option value={-1} key={-1}>
                                --Select--
                              </option>
                              {shipmentModes
                                ? shipmentModes.map(otherEntity => (
                                    <option value={otherEntity.id} key={otherEntity.id}>
                                      {otherEntity.value}
                                    </option>
                                  ))
                                : null}
                            </AvInput>
                          </Col>
                        </FormGroup>
                      </Col>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label for="paymentMode.id" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.paymentMode">Payment Mode</Translate> :
                              <span className="cargotracker-mandate-field">* </span>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvInput
                              id="shipment-info-paymentMode"
                              type="select"
                              className="form-control"
                              name="paymentModeId"
                              validate={{
                                required: { value: true, errorMessage: translate('entity.validation.required') }
                              }}
                            >
                              <option value={-1} key={-1}>
                                --Select--
                              </option>
                              {paymentModes
                                ? paymentModes.map(otherEntity => (
                                    <option value={otherEntity.id} key={otherEntity.id}>
                                      {otherEntity.value}
                                    </option>
                                  ))
                                : null}
                            </AvInput>
                          </Col>
                        </FormGroup>
                      </Col>
                    </Row>
                    <Row>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label for="trackingStatus.id" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.trackingStatus">Tracking Status</Translate> :
                              <span className="cargotracker-mandate-field">* </span>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvInput
                              id="shipment-info-trackingStatus"
                              type="select"
                              className="form-control"
                              name="trackingStatusId"
                              validate={{
                                required: { value: true, errorMessage: translate('entity.validation.required') }
                              }}
                            >
                              <option value={-1} key={-1}>
                                --Select--
                              </option>
                              {trackingStatuses
                                ? trackingStatuses.map(otherEntity => (
                                    <option value={otherEntity.id} key={otherEntity.id}>
                                      {otherEntity.value}
                                    </option>
                                  ))
                                : null}
                            </AvInput>
                          </Col>
                        </FormGroup>
                      </Col>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label for="origin.id" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.origin">Origin</Translate>
                              <span className="cargotracker-mandate-field">* </span>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvInput
                              id="shipment-info-origin"
                              type="select"
                              className="form-control"
                              name="originId"
                              validate={{
                                required: { value: true, errorMessage: translate('entity.validation.required') }
                              }}
                            >
                              <option value={-1} key={-1}>
                                --Select--
                              </option>
                              {states
                                ? states.map(otherEntity => (
                                    <option value={otherEntity.id} key={otherEntity.id}>
                                      {otherEntity.stateName}
                                    </option>
                                  ))
                                : null}
                            </AvInput>
                          </Col>
                        </FormGroup>
                      </Col>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label for="destination.id" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.destination">Destination</Translate>
                              <span className="cargotracker-mandate-field">* </span>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvInput
                              id="shipment-info-destination"
                              type="select"
                              className="form-control"
                              name="destinationId"
                              validate={{
                                required: { value: true, errorMessage: translate('entity.validation.required') }
                              }}
                            >
                              <option value={-1} key={-1}>
                                --Select--
                              </option>
                              {states
                                ? states.map(otherEntity => (
                                    <option value={otherEntity.id} key={otherEntity.id}>
                                      {otherEntity.stateName}
                                    </option>
                                  ))
                                : null}
                            </AvInput>
                          </Col>
                        </FormGroup>
                      </Col>
                    </Row>

                    <Row>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label id="actualWeightLabel" for="actualWeight" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.actualWeight">Actual Weight</Translate>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvField id="shipment-info-actualWeight" type="text" name="actualWeight" />
                          </Col>
                        </FormGroup>
                      </Col>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label id="volumetricWeightLabel" for="volumetricWeight" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.volumetricWeight">Volumetric Weight</Translate>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvField id="shipment-info-volumetricWeight" type="text" name="volumetricWeight" />
                          </Col>
                        </FormGroup>
                      </Col>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label id="lengthLabel" for="length" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.length">Length</Translate>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvField id="shipment-info-length" type="text" name="length" />
                          </Col>
                        </FormGroup>
                      </Col>
                    </Row>

                    <Row>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label id="widthLabel" for="width" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.width">Width</Translate>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvField id="shipment-info-width" type="text" name="width" />
                          </Col>
                        </FormGroup>
                      </Col>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label id="heightLabel" for="height" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.height">Height</Translate>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvField id="shipment-info-height" type="text" name="height" />
                          </Col>
                        </FormGroup>
                      </Col>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label id="quantityLabel" for="quantity" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.quantity">Quantity</Translate>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvField id="shipment-info-quantity" type="string" className="form-control" name="quantity" />
                          </Col>
                        </FormGroup>
                      </Col>
                    </Row>

                    <Row>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label id="totalFrightLabel" for="totalFright" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.totalFright">Total Fright</Translate>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvField id="shipment-info-totalFright" type="text" name="totalFright" />
                          </Col>
                        </FormGroup>
                      </Col>
                      <Col sm={8}>
                        <FormGroup row>
                          <Col sm={2}>
                            <Label id="packageDesciptionLabel" for="packageDesciption" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.packageDesciption">Package Desciption</Translate>
                            </Label>
                          </Col>
                          <Col sm={10}>
                            <AvField id="shipment-info-packageDesciption" type="textarea" name="packageDesciption" />
                          </Col>
                        </FormGroup>
                      </Col>
                    </Row>

                    <Row>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={8}>
                            <Label id="isThirdPartyLabel" check className="cargotracker-label">
                              <AvInput id="shipment-info-isThirdParty" type="checkbox" className="form-control" name="isThirdParty" />
                              <Translate contentKey="cargotrackerApp.shipmentInfo.isThirdParty">Is Third Party</Translate>
                            </Label>
                          </Col>
                          <Col sm={4} />
                        </FormGroup>
                      </Col>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label id="carrierRefNoLabel" for="carrierRefNo" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.carrierRefNo">Carrier Ref No</Translate>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvField id="shipment-info-carrierRefNo" type="text" name="carrierRefNo" />
                          </Col>
                        </FormGroup>
                      </Col>
                      <Col sm={4}>
                        <FormGroup row>
                          <Col sm={5}>
                            <Label for="carrierDetails.id" className="cargotracker-label">
                              <Translate contentKey="cargotrackerApp.shipmentInfo.carrierDetails">Carrier Details</Translate>
                            </Label>
                          </Col>
                          <Col sm={7}>
                            <AvInput id="shipment-info-carrierDetails" type="select" className="form-control" name="carrierDetailsId">
                              <option value={-1} key={-1}>
                                --Select--
                              </option>
                              {carrierDetails
                                ? carrierDetails.map(otherEntity => (
                                    <option value={otherEntity.id} key={otherEntity.id}>
                                      {otherEntity.value}
                                    </option>
                                  ))
                                : null}
                            </AvInput>
                          </Col>
                        </FormGroup>
                      </Col>
                    </Row>
                  </CardBody>
                </Card>
                {/*
                  <AvGroup>
                    
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
                  </AvGroup>*/}
                <Button tag={Link} id="cancel-save" to="/shipment-information" replace color="info">
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
  shipmentInfoEntity: storeState.shipmentInformation.entity,
  loading: storeState.shipmentInformation.loading,
  updating: storeState.shipmentInformation.updating,
  updateSuccess: storeState.shipmentInformation.updateSuccess,
  vendorId: storeState.authentication.account.vendorId
});

const mapDispatchToProps = {
  getCarierDetailsByVendorId,
  getShipmentTypesByVendorId,
  getShipmentModesByVendorId,
  getPaymentModesByVendorId,
  getTrackingStatusByVendorId,
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
