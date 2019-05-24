import React from "react";

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";

// @material-ui/icons
import Dashboard from "@material-ui/icons/Dashboard";
import LocalShipping from "@material-ui/icons/LocalShipping";
import Schedule from "@material-ui/icons/Schedule";
import DateRange from "@material-ui/icons/DateRange";
import List from "@material-ui/icons/List";

// core components
import GridContainer from "app/portal/components/Grid/GridContainer.jsx";
import GridItem from "app/portal/components/Grid/GridItem.jsx";
import NavPills from "app/portal/components/NavPills/NavPills.jsx";
import pillsStyle from "app/assets/jss/material-kit-react/views/componentsSections/pillsStyle.jsx";
import goDeliverNCRSameDay from "app/assets/img/GoDeliverNCRSameDay.png";
import goDeliverNCROverNight from "app/assets/img/GoDeliverNCROverNight.png";
import goDeliverNCRScheduled from "app/assets/img/GoDeliverNCRScheduled.png";
import classNames from "classnames";

class SectionPills extends React.Component {
  render() {
    const { classes } = this.props;
    const imageClasses = classNames(
      classes.imgRaised,
      classes.imgRoundedCircle,
      classes.imgFluid
    );
    return (
      <div className={classes.section}>
        <div className={classes.container}>
          <div id="navigation-pills">
            <div className={classes.title}>
              <h3 className={classes.title}>Go Deliver NCR</h3>
            </div>
            {/*<div className={classes.title}>
              <h3>
                <small>With Icons</small>
              </h3>
            </div>*/}
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
                        <GridItem xs={8} sm={8} md={8} lg={8}>
                        <span>
                          <p className={classes.paragrap}>
                            When you need it there Now and tomorrow's just not going to cut the mustard!
                          </p>
                          <br />
                          <p className={classes.paragrap}>
                            Call on Go Deliver NCR, we guarantee to get it there fast! 
                            Whether it's critical documents, Consignment or 3 pallets of printed 
                            literature we'll handle it, we have a varied fleet of vehicles to suite 
                            your requirements, all of our vehicles are fully tracked in real time 
                            and carry 5L worth of Goods in Transit cover so your stock is in safe 
                            hands in the event of a disaster. Fast, Efficient and Cost Effective, 
                            our clients call on us to "Make it Happen" That means No Fuss, 
                            No Drama's and No Disappointment.
                          </p>
                          <br />
                          <h4 className={classes.h4}>
                            You book it... We GO Deliver!
                          </h4>
                        </span>
                        </GridItem>
                        <GridItem xs={4} sm={4} md={4} lg={4}>
                          <div>
                            <img src={goDeliverNCRSameDay} alt="..." className={imageClasses} />
                          </div>
                        </GridItem>
                        </GridContainer>
                      )
                    },
                    {
                      tabButton: "Over Night",
                      tabIcon: Schedule,
                      tabContent: (
                        <GridContainer>
                        <GridItem xs={8} sm={8} md={8} lg={8}>
                        <span>
                          <p className={classes.paragrap}>
                            Efficiently unleash cross-media information without
                            cross-media value. Quickly maximize timely
                            deliverables for real-time schemas.
                          </p>
                          <br />
                          <p className={classes.paragrap}>
                            Dramatically maintain clicks-and-mortar solutions
                            without functional solutions. Dramatically visualize
                            customer directed convergence without revolutionary
                            ROI. Collaboratively administrate empowered markets
                            via plug-and-play networks. Dynamically
                            procrastinate B2C users after installed base
                            benefits.
                          </p>
                          <br />
                          <h4 className={classes.h4}>
                            You book it... We GO Deliver!
                          </h4>
                        </span>
                        </GridItem>
                        <GridItem xs={4} sm={4} md={4} lg={4}>
                          <div>
                            <img src={goDeliverNCROverNight} alt="..." className={imageClasses} />
                          </div>
                        </GridItem>
                        </GridContainer>
                      )
                    },
                    {
                      tabButton: "Scheduled",
                      tabIcon: DateRange,
                      tabContent: (
                        <GridContainer>
                        <GridItem xs={8} sm={8} md={8} lg={8}>
                        <span>
                          <p className={classes.paragrap}>
                            Collaboratively administrate empowered markets via
                            plug-and-play networks. Dynamically procrastinate
                            B2C users after installed base benefits.
                          </p>
                          <br />
                          <p className={classes.paragrap}>
                            Dramatically visualize customer directed convergence
                            without revolutionary ROI. Collaboratively
                            administrate empowered markets via plug-and-play
                            networks. Dynamically procrastinate B2C users after
                            installed base benefits.
                          </p>
                          <br />
                          <p className={classes.paragrap}>
                            Dramatically visualize customer directed convergence
                            without revolutionary ROI. Collaboratively
                            administrate empowered markets via plug-and-play
                            networks. Dynamically procrastinate B2C users after
                            installed base benefits.
                          </p>
                          <br />
                          <h4 className={classes.h4}>
                            You book it... We GO Deliver!
                          </h4>
                        </span>
                        </GridItem>
                        <GridItem xs={4} sm={4} md={4} lg={4}>
                          <div>
                            <img src={goDeliverNCRScheduled} alt="..." className={imageClasses} />
                          </div>
                        </GridItem>
                        </GridContainer>
                      )
                    }
                  ]}
                />
              </GridItem>
              {/*<GridItem xs={12} sm={12} md={12} lg={6}>
                <NavPills
                  color="rose"
                  horizontal={{
                    tabsGrid: { xs: 12, sm: 4, md: 4 },
                    contentGrid: { xs: 12, sm: 8, md: 8 }
                  }}
                  tabs={[
                    {
                      tabButton: "Dashboard",
                      tabIcon: Dashboard,
                      tabContent: (
                        <span>
                          <p>
                            Collaboratively administrate empowered markets via
                            plug-and-play networks. Dynamically procrastinate
                            B2C users after installed base benefits.
                          </p>
                          <br />
                          <p>
                            Dramatically visualize customer directed convergence
                            without revolutionary ROI. Collaboratively
                            administrate empowered markets via plug-and-play
                            networks. Dynamically procrastinate B2C users after
                            installed base benefits.
                          </p>
                          <br />
                          <p>
                            Dramatically visualize customer directed convergence
                            without revolutionary ROI. Collaboratively
                            administrate empowered markets via plug-and-play
                            networks. Dynamically procrastinate B2C users after
                            installed base benefits.
                          </p>
                        </span>
                      )
                    },
                    {
                      tabButton: "Schedule",
                      tabIcon: Schedule,
                      tabContent: (
                        <span>
                          <p>
                            Efficiently unleash cross-media information without
                            cross-media value. Quickly maximize timely
                            deliverables for real-time schemas.
                          </p>
                          <br />
                          <p>
                            Dramatically maintain clicks-and-mortar solutions
                            without functional solutions. Dramatically visualize
                            customer directed convergence without revolutionary
                            ROI. Collaboratively administrate empowered markets
                            via plug-and-play networks. Dynamically
                            procrastinate B2C users after installed base
                            benefits.
                          </p>
                        </span>
                      )
                    }
                  ]}
                />
                </GridItem>*/}
            </GridContainer>
          </div>
        </div>
      </div>
    );
  }
}

export default withStyles(pillsStyle)(SectionPills);
