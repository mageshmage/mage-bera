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
import { getEntity, updateEntity, createEntity, reset } from './carrier-details.reducer';
import { ICarrierDetails } from 'app/shared/model/carrier-details.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICarrierDetailsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICarrierDetailsUpdateState {
  isNew: boolean;
  vendorId: string;
}

export class CarrierDetailsUpdate extends React.Component<ICarrierDetailsUpdateProps, ICarrierDetailsUpdateState> {
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
      const { carrierDetailsEntity } = this.props;
      const entity = {
        ...carrierDetailsEntity,
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
    this.props.history.push('/entity/carrier-details');
  };

  render() {
    const { carrierDetailsEntity, vendors, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="cargotrackerApp.carrierDetails.home.createOrEditLabel">
              <Translate contentKey="cargotrackerApp.carrierDetails.home.createOrEditLabel">Create or edit a CarrierDetails</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : carrierDetailsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="carrier-details-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="valueLabel" for="value">
                    <Translate contentKey="cargotrackerApp.carrierDetails.value">Value</Translate>
                  </Label>
                  <AvField
                    id="carrier-details-value"
                    type="text"
                    name="value"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descLabel" for="desc">
                    <Translate contentKey="cargotrackerApp.carrierDetails.desc">Desc</Translate>
                  </Label>
                  <AvField id="carrier-details-desc" type="text" name="desc" />
                </AvGroup>
                <AvGroup>
                  <Label for="vendor.id">
                    <Translate contentKey="cargotrackerApp.carrierDetails.vendor">Vendor</Translate>
                  </Label>
                  <AvInput id="carrier-details-vendor" type="select" className="form-control" name="vendor.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/carrier-details" replace color="info">
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
  carrierDetailsEntity: storeState.carrierDetails.entity,
  loading: storeState.carrierDetails.loading,
  updating: storeState.carrierDetails.updating,
  updateSuccess: storeState.carrierDetails.updateSuccess
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
)(CarrierDetailsUpdate);
