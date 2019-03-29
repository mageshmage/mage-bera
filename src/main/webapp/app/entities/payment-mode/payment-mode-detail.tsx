import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './payment-mode.reducer';
import { IPaymentMode } from 'app/shared/model/payment-mode.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPaymentModeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PaymentModeDetail extends React.Component<IPaymentModeDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { paymentModeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="cargotrackerApp.paymentMode.detail.title">PaymentMode</Translate> [<b>{paymentModeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="value">
                <Translate contentKey="cargotrackerApp.paymentMode.value">Value</Translate>
              </span>
            </dt>
            <dd>{paymentModeEntity.value}</dd>
            <dt>
              <span id="desc">
                <Translate contentKey="cargotrackerApp.paymentMode.desc">Desc</Translate>
              </span>
            </dt>
            <dd>{paymentModeEntity.desc}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.paymentMode.vendor">Vendor</Translate>
            </dt>
            <dd>{paymentModeEntity.vendor ? paymentModeEntity.vendor.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/payment-mode" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/payment-mode/${paymentModeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ paymentMode }: IRootState) => ({
  paymentModeEntity: paymentMode.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PaymentModeDetail);
