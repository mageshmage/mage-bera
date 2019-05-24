import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IShipmentInfoPOD } from 'app/shared/model/shipment-info-pod.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity, reset } from './shipment-info-pod.reducer';

export interface IShipmentInfoPODDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ShipmentInfoPODDeleteDialog extends React.Component<IShipmentInfoPODDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.shipmentInfoPODEntity.id);
    this.handleClose(event);
    this.props.reset();
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { shipmentInfoPODEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>
          <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
        </ModalHeader>
        <ModalBody id="cargotrackerApp.shipmentInfoPOD.delete.question">
          <Translate contentKey="cargotrackerApp.shipmentInfoPOD.delete.question" interpolate={{ id: shipmentInfoPODEntity.id }}>
            Are you sure you want to delete this ShipmentInfoPOD?
          </Translate>
        </ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />
            &nbsp;
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>
          <Button id="jhi-confirm-delete-shipmentInfoPOD" color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />
            &nbsp;
            <Translate contentKey="entity.action.delete">Delete</Translate>
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ shipmentInfoPODSolo }: IRootState) => ({
  shipmentInfoPODEntity: shipmentInfoPODSolo.entity
});

const mapDispatchToProps = { getEntity, deleteEntity, reset };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ShipmentInfoPODDeleteDialog);
