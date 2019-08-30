import React from "react";
import withStyles from "@material-ui/core/styles/withStyles";
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import downloadStyle from "app/assets/jss/material-kit-react/views/landingPageSections/workStyle.jsx";

class Map extends React.Component {
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.section}>
        <div className={classes.container}>
          <GridContainer className={classes.textCenter} justify="center">
            <GridItem xs={12} sm={12} md={12}>
              <h2 className={classes.title}>Delivery Zones - Delhi</h2>
              <p>Delivery Zones - Delhi</p>
              {/*<iframe src="https://www.google.com/maps/d/u/0/embed?mid=1zG6659sE9THPj--h6bjH5mp0Ioul7yPA" width="1000" height="680"></iframe>*/}
              {/*<iframe src="https://www.google.com/maps/d/u/0/embed?mid=1OFWqer5JtkdESjlvgMe3m4gi28VfScte" width="800" height="500"></iframe>*/}
              {/*<iframe src="https://www.google.com/maps/d/u/0/embed?mid=1-OhkVC_hsdagC59nfDxmaFAAY0fTxB6a" width="800" height="500"></iframe>*/}
              <div className={classes.mapResponsive}>
                  <iframe src="https://www.google.com/maps/d/u/0/embed?mid=1-OhkVC_hsdagC59nfDxmaFAAY0fTxB6a&z=9" width="800" height="500" frameborder="0" allowfullscreen></iframe>
              </div>
            </GridItem>
          </GridContainer>
        </div>
      </div>
    );
  }
}

export default withStyles(downloadStyle)(Map);
