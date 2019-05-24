/*eslint-disable*/
import React from "react";

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// @material-ui/icons
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
// core components
import downloadStyle from "app/assets/jss/material-kit-react/views/landingPageSections/workStyle.jsx";

class AboutUs extends React.Component {
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.section}>
        <div className={classes.container}>
          <GridContainer className={classes.textCenter} justify="center">
            <GridItem xs={12} sm={12} md={12}>
              <h2 className={classes.title}>About Go Deliver NCR - IND</h2>
              <p className={classes.description}>
                Go Deliver NCR is specialized in the fast collection and delivery of goods throughout the NCR. When you need fast and reliable consignments to deliver your important company documents & parcels it makes perfect sense to turn to Go Deliver NCR.
              </p>
              <p className={classes.description}>
              With a varied selection of vehicles to choose from we can make sure you're getting what you need and when you need it. From bikes to large vans Go Deliver NCR have it covered, you can choose a vehicle that fits your needs and budget just as long as your goods fit the vehicle, all our vehicles are tracked and proof of your delivery will be with you within minutes of delivery being made.
              </p>
              <p className={classes.description}>
              <b>With our Head Office in Lodhi Road in New Delhi.</b>
              </p>
              <p className={classes.description}>
              Working with well-respected service partners such as <b>G.K Logistics Services, DHL, Fed Ex, DTDC and Blue Dart </b>you can choose a service that best suited to your specific needs to deliver anywhere in India. Go Deliver NCR can boast an impressive array of clients who have recognized the need for us to work with them and not just for them.
              </p>
              <h4 className={classes.description}>
              The customer service and support available at Go Deliver NCR is the most important part of the service we offer, we have technology to assist us in providing information but developing strong customer relationships is an area we have and will always continue to focus on and excel in.
              </h4>
              <h4 className={classes.description}>
              "Our very high standards of customer care is what sets us apart from our competitors"
              As a privately owned company offering a wealth of expertise in the delivery industry we have continued to develop and enhance our services, systems and our overall reputation. Go Deliver NCR offers our clients a dependable and varied range of service options to meet your needs all of which are fast, efficient and cost effective.
              </h4>
            </GridItem>
          </GridContainer>
        </div>
      </div>
    );
  }
}

export default withStyles(downloadStyle)(AboutUs);
