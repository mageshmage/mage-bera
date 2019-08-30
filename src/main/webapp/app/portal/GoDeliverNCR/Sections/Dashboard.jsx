import React from "react";
import Carousel from "react-slick";
import withStyles from "@material-ui/core/styles/withStyles";
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import Card from "app/portal/Components/Card/Card.jsx";
import carouselStyle from "app/assets/jss/material-kit-react/views/componentsSections/carouselStyle.jsx";

class Dashboard extends React.Component {
  render() {
    const { classes } = this.props;
    const settings = {
      dots: true,
      infinite: true,
      speed: 500,
      slidesToShow: 1,
      slidesToScroll: 1,
      autoplay: false
    };
    return (
      <div className={classes.section}>
        <div className={classes.container}>
          <GridContainer>
            <GridItem xs={12} sm={12} md={8} className={classes.dashboard}>
              <Card carousel>
                <Carousel {...settings}>
                  <div>
                    <img
                      src="content/images/rashtrapati.jpg"
                      alt="First slide"
                      className="slick-image"
                    />
                  </div>
                  {/*<div>
                    <img
                      src="content/images/mandir.jpg"
                      alt="Second slide"
                      className="slick-image"
                    />
                  </div>*/}
                  <div>
                    <img
                      src="content/images/birdfort.jpg"
                      alt="Second slide"
                      className="slick-image"
                    />
                  </div>
                  <div>
                    <img
                      src="content/images/redfort.jpg"
                      alt="Second slide"
                      className="slick-image"
                    />
                  </div>
                  {/*<div>
                    <img
                      src="content/images/fort.jpg"
                      alt="Second slide"
                      className="slick-image"
                    />
                  </div>*/}
                </Carousel>
              </Card>
            </GridItem>
          </GridContainer>
        </div>
      </div>
    );
  }
}

export default withStyles(carouselStyle)(Dashboard);
