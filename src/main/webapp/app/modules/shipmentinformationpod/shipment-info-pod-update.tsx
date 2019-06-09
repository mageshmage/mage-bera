import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IShipmentInfo } from 'app/shared/model/shipment-info.model';
import { getEntities as getShipmentInfos } from 'app/entities/shipment-info/shipment-info.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset, resetForNew } from './shipment-info-pod.reducer';
import { IShipmentInfoPOD } from 'app/shared/model/shipment-info-pod.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IShipmentInfoPODUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IShipmentInfoPODUpdateState {
  isNew: boolean;
  shipmentInfoId: string;
}

export class ShipmentInfoPODUpdate extends React.Component<IShipmentInfoPODUpdateProps, IShipmentInfoPODUpdateState> {
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
      //this.props.reset();
      this.props.resetForNew();
      if (
        this.props.searchValue === undefined ||
        this.props.searchValue === '' ||
        this.props.vendorId === undefined ||
        this.props.vendorId === null
      ) {
        this.handleClose();
      }
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
    //this.props.getShipmentInfos();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { shipmentInfoPODEntity } = this.props;
      const entity = {
        ...shipmentInfoPODEntity,
        ...values
      };

      if (this.state.isNew) {
        const { vendorId, searchValue } = this.props;
        entity.vendorId = vendorId;
        entity.searchValue = searchValue;
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/shipment-information-pod');
  };

  render() {
    const { shipmentInfoPODEntity, shipmentInfos, loading, updating } = this.props;
    const { isNew } = this.state;

    const { pod, podContentType } = shipmentInfoPODEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="cargotrackerApp.shipmentInfoPOD.home.createOrEditLabel" className="cargoTitle">
              <Translate contentKey="cargotrackerApp.shipmentInfoPOD.home.createOrEditLabel">Create or edit a ShipmentInfoPOD</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : shipmentInfoPODEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="shipment-info-pod-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <AvGroup>
                    <Label id="podLabel" for="pod">
                      <Translate contentKey="cargotrackerApp.shipmentInfoPOD.pod">Pod</Translate>
                    </Label>
                    <br />
                    {pod ? (
                      <div>
                        <a onClick={openFile(podContentType, pod)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {podContentType}, {byteSize(pod)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('pod')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_pod" type="file" onChange={this.onBlobChange(false, 'pod')} />
                    <AvInput type="hidden" name="pod" value={pod} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label id="commentsLabel" for="comments">
                    <Translate contentKey="cargotrackerApp.shipmentInfoPOD.comments">Comments</Translate>
                  </Label>
                  <AvField id="shipment-info-pod-comments" type="text" name="comments" />
                </AvGroup>
                {/*<AvGroup>
                  <Label for="shipmentInfo.id">
                    <Translate contentKey="cargotrackerApp.shipmentInfoPOD.shipmentInfo">Shipment Info</Translate>
                  </Label>
                  <AvInput id="shipment-info-pod-shipmentInfo" type="select" className="form-control" name="shipmentInfoId">
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
                <Button tag={Link} id="cancel-save" to="/shipment-information-pod" replace color="info">
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
  shipmentInfos: storeState.shipmentInfo.entities,
  shipmentInfoPODEntity: storeState.shipmentInfoPODSolo.entity,
  loading: storeState.shipmentInfoPODSolo.loading,
  updating: storeState.shipmentInfoPODSolo.updating,
  updateSuccess: storeState.shipmentInfoPODSolo.updateSuccess,
  vendorId: storeState.authentication.account.vendorId,
  searchValue: storeState.shipmentInfoPODSolo.search
});

const mapDispatchToProps = {
  getShipmentInfos,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
  resetForNew
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentInfoPODUpdate);
