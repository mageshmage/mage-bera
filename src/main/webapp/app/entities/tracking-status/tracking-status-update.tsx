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
import { getEntity, updateEntity, createEntity, reset } from './tracking-status.reducer';
import { ITrackingStatus } from 'app/shared/model/tracking-status.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITrackingStatusUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITrackingStatusUpdateState {
  isNew: boolean;
  vendorId: string;
}

export class TrackingStatusUpdate extends React.Component<ITrackingStatusUpdateProps, ITrackingStatusUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      vendorId: '0',
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { trackingStatusEntity } = this.props;
      const entity = {
        ...trackingStatusEntity,
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
    this.props.history.push('/entity/tracking-status');
  };

  render() {
    const { trackingStatusEntity, vendors, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="cargotrackerApp.trackingStatus.home.createOrEditLabel">
              <Translate contentKey="cargotrackerApp.trackingStatus.home.createOrEditLabel">Create or edit a TrackingStatus</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : trackingStatusEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="tracking-status-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="valueLabel" for="value">
                    <Translate contentKey="cargotrackerApp.trackingStatus.value">Value</Translate>
                  </Label>
                  <AvField
                    id="tracking-status-value"
                    type="text"
                    name="value"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descLabel" for="desc">
                    <Translate contentKey="cargotrackerApp.trackingStatus.desc">Desc</Translate>
                  </Label>
                  <AvField id="tracking-status-desc" type="text" name="desc" />
                </AvGroup>
                <AvGroup>
                  <Label for="vendor.id">
                    <Translate contentKey="cargotrackerApp.trackingStatus.vendor">Vendor</Translate>
                  </Label>
                  <AvInput id="tracking-status-vendor" type="select" className="form-control" name="vendorId">
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
                <Button tag={Link} id="cancel-save" to="/entity/tracking-status" replace color="info">
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
  vendors: storeState.vendor.entities,
  trackingStatusEntity: storeState.trackingStatus.entity,
  loading: storeState.trackingStatus.loading,
  updating: storeState.trackingStatus.updating,
  updateSuccess: storeState.trackingStatus.updateSuccess
});

const mapDispatchToProps = {
  getVendors,
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
)(TrackingStatusUpdate);
