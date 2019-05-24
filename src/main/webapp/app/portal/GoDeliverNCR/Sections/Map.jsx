{/*import React from "react";
const { compose, withProps } = require("recompose");
const {
  withScriptjs,
  withGoogleMap,
  GoogleMap,
  KmlLayer,
} = require("react-google-maps");

export const Map = compose(
  withProps({
    googleMapURL: "https://maps.googleapis.com/maps/api/js?key=AIzaSyB8-OzYTKcg1T7XE3uPHTmdHNzn9R1BTFM&v=3.exp&libraries=geometry,drawing,places",
    loadingElement: <div style={{ height: `100%` }} />,
    containerElement: <div style={{ height: `400px` }} />,
    mapElement: <div style={{ height: `100%` }} />,
  }),
  withScriptjs,
  withGoogleMap
)(props =>
    <div>
  <GoogleMap
    defaultZoom={12}
    defaultCenter={{ lat: 28.605962, lng: 77.199951 }}
    //defaultCenter={{ lat: 41.9, lng: -87.624 }}
  >
    <KmlLayer
      url="http://googlemaps.github.io/js-v2-samples/ggeoxml/cta.kml"
      options={{ preserveViewport: true }}
    />
  </GoogleMap>
  </div>
);*/}

/*eslint-disable*/
import React from "react";

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// @material-ui/icons
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
// core components
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
              <div className={classes.map}>
                <ul className={classes.mapLegend}>
                  <li className={classes.legendItemViolet}>Zone 1 (₹ 80.00)</li>
                  <li className={classes.legendItemGreen}>Zone 2 (₹ 120.00)</li>
                  <li className={classes.legendItemBlue}>Zone 3 (₹ 200.00)</li>
                </ul>
              </div>
              <p>Delivery Zones - Delhi</p>
              <iframe src="https://www.google.com/maps/d/u/0/embed?mid=1zG6659sE9THPj--h6bjH5mp0Ioul7yPA" width="1000" height="680"></iframe>
            </GridItem>
          </GridContainer>
        </div>
      </div>
    );
  }
}

export default withStyles(downloadStyle)(Map);
