import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tracking-status.reducer';
import { ITrackingStatus } from 'app/shared/model/tracking-status.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITrackingStatusDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TrackingStatusDetail extends React.Component<ITrackingStatusDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { trackingStatusEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="cargotrackerApp.trackingStatus.detail.title">TrackingStatus</Translate> [<b>{trackingStatusEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="value">
                <Translate contentKey="cargotrackerApp.trackingStatus.value">Value</Translate>
              </span>
            </dt>
            <dd>{trackingStatusEntity.value}</dd>
            <dt>
              <span id="desc">
                <Translate contentKey="cargotrackerApp.trackingStatus.desc">Desc</Translate>
              </span>
            </dt>
            <dd>{trackingStatusEntity.desc}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.trackingStatus.vendor">Vendor</Translate>
            </dt>
            <dd>{trackingStatusEntity.vendor ? trackingStatusEntity.vendor.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/tracking-status" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/tracking-status/${trackingStatusEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ trackingStatus }: IRootState) => ({
  trackingStatusEntity: trackingStatus.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TrackingStatusDetail);
