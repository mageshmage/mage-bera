import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './state.reducer';
import { IState } from 'app/shared/model/state.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class StateDetail extends React.Component<IStateDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { stateEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="cargotrackerApp.state.detail.title">State</Translate> [<b>{stateEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="stateCode">
                <Translate contentKey="cargotrackerApp.state.stateCode">State Code</Translate>
              </span>
            </dt>
            <dd>{stateEntity.stateCode}</dd>
            <dt>
              <span id="stateName">
                <Translate contentKey="cargotrackerApp.state.stateName">State Name</Translate>
              </span>
            </dt>
            <dd>{stateEntity.stateName}</dd>
            <dt>
              <Translate contentKey="cargotrackerApp.state.country">Country</Translate>
            </dt>
            <dd>{stateEntity.country ? stateEntity.country.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/state" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/state/${stateEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ state }: IRootState) => ({
  stateEntity: state.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(StateDetail);
