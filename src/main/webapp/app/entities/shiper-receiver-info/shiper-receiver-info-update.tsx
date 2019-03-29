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
import { getEntity, updateEntity, createEntity, reset } from './shiper-receiver-info.reducer';
import { IShiperReceiverInfo } from 'app/shared/model/shiper-receiver-info.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IShiperReceiverInfoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IShiperReceiverInfoUpdateState {
  isNew: boolean;
  shipmentInfoId: string;
}

export class ShiperReceiverInfoUpdate extends React.Component<IShiperReceiverInfoUpdateProps, IShiperReceiverInfoUpdateState> {
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
    if (errors.length === 0) {
      const { shiperReceiverInfoEntity } = this.props;
      const entity = {
        ...shiperReceiverInfoEntity,
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
    this.props.history.push('/entity/shiper-receiver-info');
  };

  render() {
    const { shiperReceiverInfoEntity, shipmentInfos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="cargotrackerApp.shiperReceiverInfo.home.createOrEditLabel">
              <Translate contentKey="cargotrackerApp.shiperReceiverInfo.home.createOrEditLabel">
                Create or edit a ShiperReceiverInfo
              </Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : shiperReceiverInfoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="shiper-receiver-info-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="typeLabel">
                    <Translate contentKey="cargotrackerApp.shiperReceiverInfo.type">Type</Translate>
                  </Label>
                  <AvInput
                    id="shiper-receiver-info-type"
                    type="select"
                    className="form-control"
                    name="type"
                    value={(!isNew && shiperReceiverInfoEntity.type) || 'DOCUMENT'}
                  >
                    <option value="DOCUMENT">
                      <Translate contentKey="cargotrackerApp.ShiperReceiverType.DOCUMENT" />
                    </option>
                    <option value="PARCEL">
                      <Translate contentKey="cargotrackerApp.ShiperReceiverType.PARCEL" />
                    </option>
                    <option value="PACKAGE">
                      <Translate contentKey="cargotrackerApp.ShiperReceiverType.PACKAGE" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="shipperNameLabel" for="shipperName">
                    <Translate contentKey="cargotrackerApp.shiperReceiverInfo.shipperName">Shipper Name</Translate>
                  </Label>
                  <AvField
                    id="shiper-receiver-info-shipperName"
                    type="text"
                    name="shipperName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="phoneNoLabel" for="phoneNo">
                    <Translate contentKey="cargotrackerApp.shiperReceiverInfo.phoneNo">Phone No</Translate>
                  </Label>
                  <AvField
                    id="shiper-receiver-info-phoneNo"
                    type="text"
                    name="phoneNo"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="addressLabel" for="address">
                    <Translate contentKey="cargotrackerApp.shiperReceiverInfo.address">Address</Translate>
                  </Label>
                  <AvField
                    id="shiper-receiver-info-address"
                    type="text"
                    name="address"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="cityLabel" for="city">
                    <Translate contentKey="cargotrackerApp.shiperReceiverInfo.city">City</Translate>
                  </Label>
                  <AvField
                    id="shiper-receiver-info-city"
                    type="text"
                    name="city"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="pincodeLabel" for="pincode">
                    <Translate contentKey="cargotrackerApp.shiperReceiverInfo.pincode">Pincode</Translate>
                  </Label>
                  <AvField
                    id="shiper-receiver-info-pincode"
                    type="text"
                    name="pincode"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="emailIdLabel" for="emailId">
                    <Translate contentKey="cargotrackerApp.shiperReceiverInfo.emailId">Email Id</Translate>
                  </Label>
                  <AvField id="shiper-receiver-info-emailId" type="text" name="emailId" />
                </AvGroup>
                <AvGroup>
                  <Label for="shipmentInfo.id">
                    <Translate contentKey="cargotrackerApp.shiperReceiverInfo.shipmentInfo">Shipment Info</Translate>
                  </Label>
                  <AvInput id="shiper-receiver-info-shipmentInfo" type="select" className="form-control" name="shipmentInfo.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/shiper-receiver-info" replace color="info">
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
  shiperReceiverInfoEntity: storeState.shiperReceiverInfo.entity,
  loading: storeState.shiperReceiverInfo.loading,
  updating: storeState.shiperReceiverInfo.updating,
  updateSuccess: storeState.shiperReceiverInfo.updateSuccess
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
)(ShiperReceiverInfoUpdate);
