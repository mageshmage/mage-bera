/*eslint-disable*/
import React from "react";

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// @material-ui/icons
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import Button from "app/portal/Components/CustomButtons/Button.jsx";
// core components
//import downloadStyle from "app/assets/jss/material-kit-react/views/landingPageSections/workStyle.jsx";
import downloadStyle from "app/assets/jss/material-kit-react/views/profilePage.jsx";
import profile from "app/assets/img/GoDeliverNCRJobs.png";
import classNames from "classnames";

class Jobs extends React.Component {
  render() {
    const { classes } = this.props;
    const imageClasses = classNames(
        classes.imgRaised,
        classes.imgRoundedCircle,
        classes.imgFluid
      );
    return (
      <div className={classNames(classes.main, classes.mainRaisedMain)}>
      <div>
        <div className={classes.container}>
          <GridContainer justify="center">
            <GridItem xs={12} sm={12} md={6}>
              <div className={classes.profile}>
                <div>
                  <img src={profile} alt="..." className={imageClasses} />
                </div>
                <div className={classes.name}>
                {/*<h2 className={classes.title}>Jobs With Us</h2>*/}
                <p className={classes.description}>
                Go Deliver NCR are currently looking for Full & Part Time drivers to join their expanding business, experience not essential, minimum age 21, maximum 19 points only need apply, please apply using our contact us page.
                </p>
                  <h3 className={classes.title}>Call Us Now - 
                    <span><i className={classes.socials + " fas fa-phone"} /> 91-9500302626</span>
                  </h3>
                  
                </div>
              </div>
            </GridItem>
          </GridContainer>
        </div>
      </div>
    </div>
    );
  }
}

export default withStyles(downloadStyle)(Jobs);
