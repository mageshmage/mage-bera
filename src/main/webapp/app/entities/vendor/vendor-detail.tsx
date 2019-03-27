import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vendor.reducer';
import { IVendor } from 'app/shared/model/vendor.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVendorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VendorDetail extends React.Component<IVendorDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { vendorEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="cargotrackerApp.vendor.detail.title">Vendor</Translate> [<b>{vendorEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="cargotrackerApp.vendor.name">Name</Translate>
              </span>
            </dt>
            <dd>{vendorEntity.name}</dd>
            <dt>
              <span id="pan">
                <Translate contentKey="cargotrackerApp.vendor.pan">Pan</Translate>
              </span>
            </dt>
            <dd>{vendorEntity.pan}</dd>
            <dt>
              <span id="gstin">
                <Translate contentKey="cargotrackerApp.vendor.gstin">Gstin</Translate>
              </span>
            </dt>
            <dd>{vendorEntity.gstin}</dd>
          </dl>
          <Button tag={Link} to="/entity/vendor" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/vendor/${vendorEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ vendor }: IRootState) => ({
  vendorEntity: vendor.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VendorDetail);
