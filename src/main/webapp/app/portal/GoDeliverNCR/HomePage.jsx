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
//import WorkSection from "./Sections/WorkSection.jsx";
import SectionCarousel from "./Sections/SectionCarousel.jsx";
import AboutUs from "./Sections/AboutUs.jsx";
import Jobs from "./Sections/Jobs";
import Map from "./Sections/Map";
import SectionPills from "./Sections/SectionPills";
import Dashboard from "./Sections/Dashboard";
import "./style.css";
import CustomInput from "app/portal/components/CustomInput/CustomInput.jsx";
import Search from "@material-ui/icons/Search";
import ContactUs from "./Sections/ContactUS";
import Tracker from "./Sections/Tracker";
import { LocationOnOutlined } from "@material-ui/icons";

const dashboardRoutes = [];

class HomePage extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
        classicModal: false
    };
}

handleClickOpen(modal) {
  var x = [];
  x[modal] = true;
  this.setState(x);
}

onModalClose = event =>{
  //alert('onModalClose');
  var x = [];
  x['classicModal'] = false;
  this.setState(x);
}

  render() {
    console.log('test - ' + this.state.classicModal);
    const { classes, ...rest } = this.props;
    return (
      <div>
        <Header
          routes={dashboardRoutes}
          color="godeliver"
          brand="Go Deliver NCR"
          rightLinks={
            <div>
              
                <Button
                  color="transparent"
                  className={classes.navLink}
                  onClick={() => this.handleClickOpen("classicModal")}
                >
                  <LocationOnOutlined className={classes.icons} /> Tracking
                </Button>
            
              {/*<CustomInput
                white
                inputRootCustomClasses={classes.inputRootCustomClasses}
                formControlProps={{
                  className: classes.formControl
                }}
                inputProps={{
                  placeholder: "Enter Tracking Id",
                  inputProps: {
                    "aria-label": "Search",
                    className: classes.searchInput
                  }
                }}
              />
              <Button justIcon round color="white">
                <Search className={classes.searchIcon} />
              </Button>*/}
            </div>
          }
          fixed
          changeColorOnScroll={{
            height: 400,
            color: "white"
          }}
          {...rest}
        />
        {this.state.classicModal &&(
          <Tracker parentState={this.state.classicModal} onModalClose= {this.onModalClose}/>
          )
        }
        {/* image={require("app/assets/img/plane.jpg")}*/}
        <Parallax filter >
          <div className={classes.container}>
            <GridContainer>
              <GridItem xs={12} sm={12} md={12} className={classes.titleAlign}>
                {/*<h1 className={classes.title}>MOVES COMES CHEAPER THIS FALL.</h1>
                <h4>
                This Fall the Prices on All the Local and Long Distance Moves We Offer Will Drop Dramatically!
                </h4>
                <br />*/}
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
                <Dashboard />
              </GridItem>
            </GridContainer>
          </div>
        </Parallax>
        <div className={classNames(classes.main, classes.mainRaised)}>
          <div className={classes.container}>
            <SectionPills />
            <ProductSection />
            <SectionCarousel />
            {/*<TeamSection />*/}
            <Map />
            <AboutUs />
            <Jobs />
            <ContactUs />
          </div>
        </div>
        <Footer />
      </div>
    );
  }
}

export default withStyles(landingPageStyle)(HomePage);
