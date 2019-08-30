import React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import { Home, LocalShipping, DirectionsRailway, Flight }  from "@material-ui/icons";
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import InfoArea from "app/portal/Components/InfoArea/InfoArea.jsx";
import productStyle from "app/assets/jss/material-kit-react/views/landingPageSections/productStyle.jsx";

class ProductSection extends React.Component {
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.section}>
        <GridContainer justify="center">
          <GridItem xs={12} sm={12} md={8}>
            <h2 className={classes.title}>At Our Service</h2>
          </GridItem>
        </GridContainer>
        <div>
          <GridContainer>
            <GridItem xs={12} sm={12} md={3}>
              <InfoArea
                title="Door To Door"
                description="Door to Door delivery is the most common and convenient way of shipment for the customer"
                icon={Home}
                iconColor="godeliver"
                vertical
              />
            </GridItem>
            <GridItem xs={12} sm={12} md={3}>
              <InfoArea
                title="Ground Cargo"
                description="With our own fleet vehicles, we provide an efficient, competitive and professional transport service via road transportation."
                icon={LocalShipping}
                iconColor="godeliver"
                vertical
              />
            </GridItem>
            <GridItem xs={12} sm={12} md={3}>
              <InfoArea
                title="Train Cargo"
                description="Excellent quality, easy to use and reasonable price are just few to mention features of our highlighted train freight services."
                icon={DirectionsRailway}
                iconColor="godeliver"
                vertical
              />
            </GridItem>
            <GridItem xs={12} sm={12} md={3}>
              <InfoArea
                title="Air Cargo"
                description="We are known for efficient and reliable air-freight forwarding services throughout the globe, with the years of experience and trust of our clients and customers."
                icon={Flight}
                iconColor="godeliver"
                vertical
              />
            </GridItem>
          </GridContainer>
        </div>
      </div>
    );
  }
}

export default withStyles(productStyle)(ProductSection);
