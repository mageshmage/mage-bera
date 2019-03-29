import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shiper-receiver-info.reducer';
import { IShiperReceiverInfo } from 'app/shared/model/shiper-receiver-info.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShiperReceiverInfoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ShiperReceiverInfoDetail extends React.Component<IShiperReceiverInfoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { shiperReceiverInfoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="cargotrackerApp.shiperReceiverInfo.detail.title">ShiperReceiverInfo</Translate> [<b>
              {shiperReceiverInfoEntity.id}
            </b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="type">
                <Translate contentKey="cargotrackerApp.shiperReceiverInfo.type">Type</Translate>
              </span>
            </dt>
            <dd>{shiperReceiverInfoEntity.type}</dd>
            <dt>
              <span id="shipperName">
                <Translate contentKey="cargotrackerApp.shiperReceiverInfo.shipperName">Shipper Name</Translate>
              </span>
            </dt>
            <dd>{shiperReceiverInfoEntity.shipperName}</dd>
            <dt>
              <span id="phoneNo">
                <Translate contentKey="cargotrackerApp.shiperReceiverInfo.phoneNo">Phone No</Translate>
              </span>
            </dt>
            <dd>{shiperReceiverInfoEntity.phoneNo}</dd>
            <dt>
              <span id="address">
                <Translate contentKey="cargotrackerApp.shiperReceiverInfo.address">Address</Translate>
              </span>
            </dt>
            <dd>{shiperReceiverInfoEntity.address}</dd>
            <dt>
              <span id="city">
                <Translate contentKey="cargotrackerApp.shiperReceiverInfo.city">City</Translate>
              </span>
            </dt>
            <dd>{shiperReceiverInfoEntity.city}</dd>
            <dt>
              <span id="pincode">
                <Translate contentKey="cargotrackerApp.shiperReceiverInfo.pincode">Pincode</Translate>
              </span>
            </dt>
            <dd>{shiperReceiverInfoEntity.pincode}</dd>
            <dt>
              <span id="emailId">
                <Translate contentKey="cargotrackerApp.shiperReceiverInfo.emailId">Email Id</Translate>
              </span>
            </dt>
            <dd>{shiperReceiverInfoEntity.emailId}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.shiperReceiverInfo.shipmentInfo">Shipment Info</Translate>
            </dt>
            <dd>{shiperReceiverInfoEntity.shipmentInfo ? shiperReceiverInfoEntity.shipmentInfo.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/shiper-receiver-info" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/shiper-receiver-info/${shiperReceiverInfoEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ shiperReceiverInfo }: IRootState) => ({
  shiperReceiverInfoEntity: shiperReceiverInfo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShiperReceiverInfoDetail);
