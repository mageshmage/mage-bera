import React from "react";
// nodejs library that concatenates classes
import classNames from "classnames";
// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";

// @material-ui/icons

// core components
import Header from "app/portal/Components/Header/Header.jsx";
import Footer from "app/portal/Components/Footer/Footer.jsx";
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import Button from "app/portal/Components/CustomButtons/Button.jsx";
import HeaderLinks from "app/portal/Components/Header/HeaderLinks.jsx";
import Parallax from "app/portal/Components/Parallax/Parallax.jsx";

import landingPageStyle from "app/assets/jss/material-kit-react/views/landingPage.jsx";

// Sections for this page
import ProductSection from "./Sections/ProductSection.jsx";
import TeamSection from "./Sections/TeamSection.jsx";
import WorkSection from "./Sections/WorkSection.jsx";
import SectionCarousel from "./Sections/SectionCarousel.jsx";
import AboutUs from "./Sections/AboutUs.jsx"

const dashboardRoutes = [];

class LandingPage extends React.Component {
  render() {
    const { classes, ...rest } = this.props;
    return (
      <div>
        <Header
          color="transparent"
          routes={dashboardRoutes}
          brand="GK Logistics Services"
          rightLinks={<HeaderLinks />}
          fixed
          changeColorOnScroll={{
            height: 400,
            color: "white"
          }}
          {...rest}
        />
        <Parallax filter image={require("app/assets/img/plane.jpg")}>
          <div className={classes.container}>
            <GridContainer>
              <GridItem xs={12} sm={12} md={12} className={classes.titleAlign}>
                <h1 className={classes.title}>MOVES COMES CHEAPER THIS FALL.</h1>
                <h4>
                This Fall the Prices on All the Local and Long Distance Moves We Offer Will Drop Dramatically!
                </h4>
                <br />
                {/*<Button
                  color="danger"
                  size="lg"
                  href="https://www.youtube.com/watch?v=dQw4w9WgXcQ"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  <i className="fas fa-play" />
                  Watch video
                </Button>*/}
              </GridItem>
            </GridContainer>
          </div>
        </Parallax>
        <div className={classNames(classes.main, classes.mainRaised)}>
          <div className={classes.container}>
            <ProductSection />
            {/*<SectionCarousel />
            <TeamSection />*/}
            <AboutUs />
          </div>
        </div>
        <Footer />
      </div>
    );
  }
}

export default withStyles(landingPageStyle)(LandingPage);
