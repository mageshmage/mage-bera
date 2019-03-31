import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './carrier-details.reducer';
import { ICarrierDetails } from 'app/shared/model/carrier-details.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICarrierDetailsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CarrierDetailsDetail extends React.Component<ICarrierDetailsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { carrierDetailsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="cargotrackerApp.carrierDetails.detail.title">CarrierDetails</Translate> [<b>{carrierDetailsEntity.id}</b>
            ]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="value">
                <Translate contentKey="cargotrackerApp.carrierDetails.value">Value</Translate>
              </span>
            </dt>
            <dd>{carrierDetailsEntity.value}</dd>
            <dt>
              <span id="desc">
                <Translate contentKey="cargotrackerApp.carrierDetails.desc">Desc</Translate>
              </span>
            </dt>
            <dd>{carrierDetailsEntity.desc}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.carrierDetails.vendor">Vendor</Translate>
            </dt>
            <dd>{carrierDetailsEntity.vendorId ? carrierDetailsEntity.vendorId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/carrier-details" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/carrier-details/${carrierDetailsEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ carrierDetails }: IRootState) => ({
  carrierDetailsEntity: carrierDetails.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CarrierDetailsDetail);
