import React from "react";
import Carousel from "react-slick";
import withStyles from "@material-ui/core/styles/withStyles";
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import Card from "app/portal/Components/Card/Card.jsx";
import carouselStyle from "app/assets/jss/material-kit-react/views/componentsSections/carouselStyle.jsx";

class SectionCarousel extends React.Component {
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
            <GridItem xs={12} sm={12} md={8} className={classes.marginAuto}>
              <Card carousel>
                <Carousel {...settings}>
                  <div>
                    <img
                      src="content/images/air.jpg"
                      alt="First slide"
                      className="slick-image"
                    />
                    <div className="slick-caption">
                        <h3>Air Cargo</h3>
                        <p class="text-muted">Shipping</p>
                    </div>
                  </div>
                  <div>
                    <img
                      src="content/images/train.jpg"
                      alt="Second slide"
                      className="slick-image"
                    />
                    <div className="slick-caption">
                        <h3>Train Cargo</h3>
                        <p class="text-muted">Distribution</p>
                    </div>
                  </div>
                  <div>
                    <img
                      src="content/images/road.jpg"
                      alt="Third slide"
                      className="slick-image"
                    />
                    <div className="slick-caption">
                        <h3>Ground Cargo</h3>
                        <p class="text-muted">Carting</p>
                    </div>
                  </div>
                  <div>
                    <img
                      src="content/images/doortodoor.jpg"
                      alt="Third slide"
                      className="slick-image"
                    />
                    <div className="slick-caption">
                        <h3>Door To Door</h3>
                        <p class="text-muted">Shipping</p>
                    </div>
                  </div>
                </Carousel>
              </Card>
            </GridItem>
          </GridContainer>
        </div>
      </div>
    );
  }
}

export default withStyles(carouselStyle)(SectionCarousel);
