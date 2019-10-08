import React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import LocalShipping from "@material-ui/icons/LocalShipping";
import Schedule from "@material-ui/icons/Schedule";
import DateRange from "@material-ui/icons/DateRange";
import GridContainer from "app/portal/components/Grid/GridContainer.jsx";
import GridItem from "app/portal/components/Grid/GridItem.jsx";
import NavPills from "app/portal/components/NavPills/NavPills.jsx";
import pillsStyle from "app/assets/jss/material-kit-react/views/componentsSections/pillsStyle.jsx";
import classNames from "classnames";

class SectionPills extends React.Component {
  render() {
    const { classes } = this.props;
    const imageClasses = classNames(
      classes.imgRaised,
      classes.imgRoundedCircle,
      classes.imgFluid,
      classes.margin30
    );
    return (
      <div className={classes.section}>
        <div className={classes.container}>
          <div id="navigation-pills">
            <div className={classes.title}>
              <h3 className={classes.title}>Go Deliver NCR</h3>
            </div>
            <GridContainer>
              <GridItem xs={12} sm={12} md={12} lg={12}>
                <NavPills
                  color="godeliver"
                  tabs={[
                    {
                      tabButton: "Same Day",
                      tabIcon: LocalShipping,
                      tabContent: (
                        <GridContainer>
                          <GridItem xs={4} sm={4} md={4} lg={4}>
                            <div>
                              <img src="content/images/godeliversameday230.png" alt="..." className={imageClasses} />
                            </div>
                          </GridItem>
                        <GridItem xs={8} sm={8} md={8} lg={8}>
                          <span>
                            <p className={classes.paragrap}>
                            <b className={classes.goBold}>Go Deliver NCR</b> has a number of <b className={classes.goBold}>NCR Same-Day</b> delivery solutions which are ideally placed for your less critical but still time sensitive shipping requirements thus providing a more economical solution. 
                            </p>
                            <p className={classes.paragrap}>
                            You can choose our standard <b className={classes.goBold}>Same-Day</b> solution which guarantees delivery <b className={classes.goBold}>before 6pm</b>, or you can choose from delivery by as early as 
                            <b className={classes.goBold}> 9am, 10.30 or 12.00</b>. We can also give you a range of Saturday delivery options.
                            </p>
                            <p className={classes.paragrap}>
                            All of our parcels benefit from live tracking and receive a signature upon delivery to give you piece of mind and put you in the driver's seat.
                            </p>
                            <h4 className={classes.h4}>
                              Direct to Every direction !!!
                            </h4>
                          </span>
                        </GridItem>
                        </GridContainer>
                      )
                    },
                    {
                      tabButton: "Over Night",
                      tabIcon: Schedule,
                      tabContent: (
                        <GridContainer>
                          <GridItem xs={4} sm={4} md={4} lg={4}>
                            <div>
                              <img src="content/images/godeliverovernight230.png" alt="..." className={imageClasses} />
                            </div>
                          </GridItem>
                        <GridItem xs={8} sm={8} md={8} lg={8}>
                          <span>
                            <p className={classes.paragrap}>
                              <b className={classes.goBold}>Go Deliver NCR</b> has a number of NCR Overnight
                              delivery solutions which are ideally placed for your
                              less critical but still time sensitive shipping
                              requirements thus providing a more economical
                              solution. 
                            </p>
                            <p className={classes.paragrap}>
                              You can choose our standard Overnight
                              solution which guarantees delivery between <b className={classes.goBold}>7:00pm
                              to 1:00am</b>, or you can choose from delivery by as early
                              as <b className={classes.goBold}>7:00am to 9:00am</b>. We can also give you a range of
                              Saturday and Sunday delivery options, with
                              reasonable charges extra.
                            </p>
                            <p className={classes.paragrap}>
                              All of our parcels benefit from live tracking and
                              receive a signature upon delivery to give you piece of
                              mind and put you in the driver&#39;s seat.
                            </p>
                            <p className={classes.paragrap}>
                              Over Night Charges Extra As Per Requirements!
                            </p>
                            <h4 className={classes.h4}>
                            Direct to Every direction !!!
                            </h4>
                          </span>
                        </GridItem>
                        
                        </GridContainer>
                      )
                    },
                    {
                      tabButton: "Scheduled",
                      tabIcon: DateRange,
                      tabContent: (
                        <GridContainer>
                          <GridItem xs={4} sm={4} md={4} lg={4}>
                            <div>
                              <img src="content/images/godeliverscheduled230.png" alt="..." className={imageClasses} />
                            </div>
                          </GridItem>
                          <GridItem xs={8} sm={8} md={8} lg={8}>
                          <span>
                            <p className={classes.paragrap}>
                              A <b className={classes.goBold}>Go Delivery Schedule</b> specifies the time for
                              delivery of goods or services from a supplier to a
                              customer, and often covers a period of time.
                            </p>
                            <p className={classes.paragrap}>
                              The <b className={classes.goBold}>Go Delivery Schedule</b> Date is the date a shipment
                              is scheduled for delivery. It can be done by a clients or
                              customer based on their delivery time. It has to be
                              done in delivery schedule detailed table or Go Deliver
                              NCR contacts you by phone and sets up the delivery
                              schedule.
                            </p>
                            <p className={classes.paragrap}>
                              The <b className={classes.goBold}>Scheduled Delivery</b> Date is determined when Go
                              Deliver NCR receives the shipment information. If an
                              exception occurs that changes this date, a
                              rescheduled delivery date will be provided.
                            </p>
                            <h4 className={classes.h4}>
                            Direct to Every direction !!!
                            </h4>
                          </span>
                          </GridItem>
                        </GridContainer>
                      )
                    }
                  ]}
                />
              </GridItem>
            </GridContainer>
          </div>
        </div>
      </div>
    );
  }
}

export default withStyles(pillsStyle)(SectionPills);
