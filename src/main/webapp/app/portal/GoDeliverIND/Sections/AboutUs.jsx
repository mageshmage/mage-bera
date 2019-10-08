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
                    {/*<h2 className={classes.title}>Jobs With Us</h2>*/}
                    <p className={classes.description}>
                      <b className={classes.goBold}>Go Deliver IND</b> is specialized in the fast collection and delivery of goods throughout the IND.
                      When you need fast and reliable consignments to deliver your important company documents & parcels it makes perfect sense to turn to Go Deliver IND.
              </p>
                    {/*<p className={classes.description}>
              With a varied selection of vehicles to choose from we can make sure you're getting what you need and when you need it. 
              From bikes to large vans Go Deliver NCR have it covered, you can choose a vehicle that fits your needs and budget just as long as your goods fit the vehicle, all our vehicles are tracked and proof of your delivery will be with you within minutes of delivery being made.
              </p>*/}
                    <p className={classes.description}>
                      <b className={classes.goBold}>With our Head Office in Lodhi Road in New Delhi.</b>
                    </p>
                    <p className={classes.description}>
                      Working with well-respected service partners such as <b className={classes.goBold}>G.K Logistics Services, DHL, Fed Ex, DTDC and Blue Dart </b>
                      you can choose a service that best suited to your specific needs to deliver anywhere in India.
                      <b className={classes.goBold}>Go Deliver IND</b> can boast an impressive array of clients who have recognized the need for us to work with them and not just for them.
              </p>
                    <p className={classes.description}>
                      The customer service and support available at <b className={classes.goBold}>Go Deliver IND</b> is the most important part of the service we offer,
                      we have technology to assist us in providing information but developing strong customer relationships is an area
                      we have and will always continue to focus on and excel in.
              </p>
                    {/*<p className={classes.description}>
              "Our very high standards of customer care is what sets us apart from our competitors"
              As a privately owned company offering a wealth of expertise in the delivery industry we have continued to develop and enhance our services, systems and our overall reputation. Go Deliver NCR offers our clients a dependable and varied range of service options to meet your needs all of which are fast, efficient and cost effective.
            </p>*/}
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
