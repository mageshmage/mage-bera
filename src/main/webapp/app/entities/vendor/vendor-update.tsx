import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './vendor.reducer';
import { IVendor } from 'app/shared/model/vendor.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVendorUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVendorUpdateState {
  isNew: boolean;
}

export class VendorUpdate extends React.Component<IVendorUpdateProps, IVendorUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { vendorEntity } = this.props;
      const entity = {
        ...vendorEntity,
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
    this.props.history.push('/entity/vendor');
  };

  render() {
    const { vendorEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="cargotrackerApp.vendor.home.createOrEditLabel">
              <Translate contentKey="cargotrackerApp.vendor.home.createOrEditLabel">Create or edit a Vendor</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : vendorEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="vendor-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="vendornameLabel" for="vendorname">
                    <Translate contentKey="cargotrackerApp.vendor.vendorname">Vendorname</Translate>
                  </Label>
                  <AvField
                    id="vendor-vendorname"
                    type="text"
                    name="vendorname"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="firstNameLabel" for="firstName">
                    <Translate contentKey="cargotrackerApp.vendor.firstName">First Name</Translate>
                  </Label>
                  <AvField
                    id="vendor-firstName"
                    type="text"
                    name="firstName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="lastName">
                    <Translate contentKey="cargotrackerApp.vendor.lastName">Last Name</Translate>
                  </Label>
                  <AvField
                    id="vendor-lastName"
                    type="text"
                    name="lastName"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="mobileNoLabel" for="mobileNo">
                    <Translate contentKey="cargotrackerApp.vendor.mobileNo">Mobile No</Translate>
                  </Label>
                  <AvField
                    id="vendor-mobileNo"
                    type="text"
                    name="mobileNo"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="addressLabel" for="address">
                    <Translate contentKey="cargotrackerApp.vendor.address">Address</Translate>
                  </Label>
                  <AvField
                    id="vendor-address"
                    type="text"
                    name="address"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="panLabel" for="pan">
                    <Translate contentKey="cargotrackerApp.vendor.pan">Pan</Translate>
                  </Label>
                  <AvField
                    id="vendor-pan"
                    type="text"
                    name="pan"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="gstInLabel" for="gstIn">
                    <Translate contentKey="cargotrackerApp.vendor.gstIn">Gst In</Translate>
                  </Label>
                  <AvField
                    id="vendor-gstIn"
                    type="text"
                    name="gstIn"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="isActiveLabel" check>
                    <AvInput id="vendor-isActive" type="checkbox" className="form-control" name="isActive" />
                    <Translate contentKey="cargotrackerApp.vendor.isActive">Is Active</Translate>
                  </Label>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/vendor" replace color="info">
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
  vendorEntity: storeState.vendor.entity,
  loading: storeState.vendor.loading,
  updating: storeState.vendor.updating,
  updateSuccess: storeState.vendor.updateSuccess
});

const mapDispatchToProps = {
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
)(VendorUpdate);
