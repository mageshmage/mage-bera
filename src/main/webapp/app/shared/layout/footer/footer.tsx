import './footer.css';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';
import { translate } from 'react-jhipster';
import { Link } from 'react-router-dom';

const Footer = props => (
  <div className="footer page-content">
    <p>
      {translate('footer')}
      <Link to="/profile-page">Cargo Tracker</Link>
    </p>
  </div>
);

export default Footer;
