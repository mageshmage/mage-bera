import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, FormGroup, Card, CardBody, CardTitle } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shipment-information.reducer';
import { IShipmentInfo } from 'app/shared/model/shipment-info.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';

export interface IShipmentInformationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ShipmentInformationDetail extends React.Component<IShipmentInformationDetailProps> {
  componentDidMount() {
    //alert('ShipmentInformationDetail');
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { shipmentInfoEntity } = this.props;
    return (
      <Row className="justify-content-center">
        <Col md="12">
          <AvForm model={shipmentInfoEntity}>
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
                            <input readOnly value={shipmentInfoEntity.shipperInfo.name} className="form-control" type="text" />
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
                            <input readOnly value={shipmentInfoEntity.shipperInfo.phoneNo} className="form-control" type="text" />
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
                            <textarea readOnly value={shipmentInfoEntity.shipperInfo.address} className="form-control" />
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
                            <input readOnly value={shipmentInfoEntity.shipperInfo.city} className="form-control" type="text" />
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
                            <input readOnly value={shipmentInfoEntity.shipperInfo.pincode} className="form-control" type="text" />
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
                            <input readOnly value={shipmentInfoEntity.shipperInfo.emailId} className="form-control" type="text" />
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
                            <input readOnly value={shipmentInfoEntity.receiverInfo.name} className="form-control" type="text" />
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
                            <input readOnly value={shipmentInfoEntity.receiverInfo.phoneNo} className="form-control" type="text" />
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
                            <textarea readOnly value={shipmentInfoEntity.shipperInfo.address} className="form-control" />
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
                            <input readOnly value={shipmentInfoEntity.receiverInfo.city} className="form-control" type="text" />
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
                            <input readOnly value={shipmentInfoEntity.receiverInfo.pincode} className="form-control" type="text" />
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
                            <input readOnly value={shipmentInfoEntity.receiverInfo.emailId} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.consignmentNo} className="form-control" type="text" />
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
                        <input
                          readOnly
                          value={convertDateTimeFromServer(shipmentInfoEntity.bookingDate)}
                          className="form-control"
                          type="text"
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
                        <input
                          readOnly
                          value={convertDateTimeFromServer(shipmentInfoEntity.expectedDeliveryDate)}
                          className="form-control"
                          type="text"
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
                        <input readOnly value={shipmentInfoEntity.shipmentTypeValue} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.shipmentModeValue} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.paymentModeValue} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.trackingStatusValue} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.originValue} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.destinationValue} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.actualWeight} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.volumetricWeight} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.length} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.width} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.height} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.quantity} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.totalFright} className="form-control" type="text" />
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
                        <textarea readOnly value={shipmentInfoEntity.packageDesciption} className="form-control" />
                      </Col>
                    </FormGroup>
                  </Col>
                </Row>

                <Row>
                  <Col sm={4}>
                    <FormGroup row>
                      <Col sm={2}>
                        <Label id="isThirdPartyLabel" for="packageDesciption" className="cargotracker-label">
                          <Translate contentKey="cargotrackerApp.shipmentInfo.isThirdParty">Is Third Party</Translate>
                        </Label>
                      </Col>
                      <Col sm={10}>
                        <input type="checkbox" readOnly checked={shipmentInfoEntity.isThirdParty} className="form-control" />
                      </Col>
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
                        <input readOnly value={shipmentInfoEntity.carrierRefNo} className="form-control" type="text" />
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
                        <input readOnly value={shipmentInfoEntity.carrierDetailsValue} className="form-control" type="text" />
                      </Col>
                    </FormGroup>
                  </Col>
                </Row>
              </CardBody>
            </Card>
            <Button tag={Link} to="/shipment-information" replace color="info">
              <FontAwesomeIcon icon="arrow-left" />{' '}
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.back">Back</Translate>
              </span>
            </Button>
            &nbsp;
            <Button tag={Link} to={`/shipment-information/${shipmentInfoEntity.id}/edit`} replace color="primary">
              <FontAwesomeIcon icon="pencil-alt" />{' '}
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.edit">Edit</Translate>
              </span>
            </Button>
          </AvForm>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ shipmentInformation }: IRootState) => ({
  shipmentInfoEntity: shipmentInformation.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentInformationDetail);
