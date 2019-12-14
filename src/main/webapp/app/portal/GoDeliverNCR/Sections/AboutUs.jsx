import React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import downloadStyle from "app/assets/jss/material-kit-react/views/profilePage.jsx";
import classNames from "classnames";
class AboutUs extends React.Component {
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
                    <img src="content/images/godelivercontact230.png" alt="..." className={imageClasses} />
                  </div>
                  <div className={classes.name}>
                    <p className={classes.description}>
                      <b className={classes.goBold}>Go Deliver NCR</b> is specialized in the fast collection and delivery of goods throughout the NCR.
                      When you need fast and reliable consignments to deliver your important company documents & parcels it makes perfect sense to turn to Go Deliver NCR.
                    </p>
                    <p className={classes.description}>
                      <b className={classes.goBold}>With our Head Office in Lodhi Road in New Delhi.</b>
                    </p>
                    {/*<p className={classes.description}>
                      Working with well-respected service partners such as <b className={classes.goBold}>G.K Logistics Services, DHL, Fed Ex, DTDC and Blue Dart </b>
                      you can choose a service that best suited to your specific needs to deliver anywhere in India.
                      Go Deliver NCR can boast an impressive array of clients who have recognized the need for us to work with them and not just for them.
                    </p>*/}
                    <p className={classes.description}>
                      The customer service and support available at <b className={classes.goBold}>Go Deliver NCR</b> is the most important part of the service we offer,
                      we have technology to assist us in providing information but developing strong customer relationships is an area
                      we have and will always continue to focus on and excel in.
                    </p>
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

export default withStyles(downloadStyle)(AboutUs);
