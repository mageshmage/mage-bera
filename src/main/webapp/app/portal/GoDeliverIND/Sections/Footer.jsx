import React from "react";
import PropTypes from "prop-types";
import classNames from "classnames";
import { withStyles } from "@material-ui/core";
import { Link } from "react-router-dom";
import Favorite from "@material-ui/icons/Favorite";
import footerStyle from "./footerStyle.jsx";
import ContactUs from "./ContactUs";

function Footer({ ...props }) {
  const { classes, whiteFont } = props;
  const footerClasses = classNames({
    [classes.footer]: true,
    [classes.footerWhiteFont]: whiteFont
  });
  const aClasses = classNames({
    [classes.a]: true,
    [classes.footerWhiteFont]: whiteFont
  });
  return (
    <footer className={footerClasses}>
      <div className={classes.container}>
        <div className={classes.thirty} data-anim-type="fadeInUp" data-anim-delay="100">
          <img src="content/images/godeliverind230.png" className={classes.logo} alt="Logo" />
        </div>
        <div className={classes.seventy} data-anim-type="fadeInUp" data-anim-delay="200">
          <div className={classes.qlinks}>
            <h4 className={classes.h4}>Contact Us</h4>
            <ul className={classes.ul}>
              <li className={classes.li}><i className="fa fa-home fa-lg"></i>&nbsp; B-77, B.K Dutt Colony, Lodhi Road, New Delhi - 110003</li>
              <li className={classes.li}><i className="fa fa-phone"></i>&nbsp; 91-9500302626</li>
              <li className={classes.li}><a className={classes.a} href="mailto:godeliverind@gmail.com"><i className="fa fa-envelope"></i>&nbsp; godeliverind@gmail.com</a></li>
            </ul>
          </div>
        </div>
        {/*<div className={classes.oneFifth} data-anim-type="fadeInUp" data-anim-delay="300">
          <ContactUs />
  </div>*/}
      </div>
      <ContactUs />
      <div className={classes.footerBottom}>
        &copy; {1900 + new Date().getYear()} , made with{" "}
        <Favorite className={classes.icon} /> by{" "}
        <Link to="/profile-page" className={aClasses}>
          Cargo Tracker
                      </Link>
      </div>
    </footer>
  );
}

Footer.propTypes = {
  classes: PropTypes.object.isRequired,
  whiteFont: PropTypes.bool
};

export default withStyles(footerStyle)(Footer);
