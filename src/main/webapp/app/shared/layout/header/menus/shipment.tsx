import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const ShipmentMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="truck" name={translate('global.menu.shipment.main')} id="entity-menu">
    <DropdownItem tag={Link} to="/shipment-information">
      <FontAwesomeIcon icon="envelope" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.shipment.information" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/shipment-tracking">
      <FontAwesomeIcon icon="map-marker" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.shipment.tracking" />
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/shipment-info-pod">
      <FontAwesomeIcon icon="upload" fixedWidth />
      &nbsp;
      <Translate contentKey="global.menu.shipment.uploadpod" />
    </DropdownItem>
  </NavDropdown>
);
