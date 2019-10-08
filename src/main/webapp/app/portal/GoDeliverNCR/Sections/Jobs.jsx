import React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import downloadStyle from "app/assets/jss/material-kit-react/views/profilePage.jsx";
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
                  <img src="content/images/godeliverjobs230.png" alt="..." className={imageClasses} />
                </div>
                <div className={classes.name}>
                <p className={classes.description}>
                <b className={classes.goBold}>Go Deliver NCR</b> are currently looking for Full & Part Time drivers to join their expanding business, 
                experience not essential, <b className={classes.goBold}>minimum age 21</b>, please apply using our contact us page.
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
