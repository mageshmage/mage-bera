import React from "react";
import classNames from "classnames";
import withStyles from "@material-ui/core/styles/withStyles";
import Header from "./Sections/Header/Header.jsx";
import Footer from "./Sections/Footer.jsx";
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import Button from "app/portal/Components/CustomButtons/Button.jsx";
import Parallax from "app/portal/Components/Parallax/Parallax.jsx";
import landingPageStyle from "app/assets/jss/material-kit-react/views/landingPage.jsx";
import ProductSection from "./Sections/ProductSection.jsx";
import AboutUs from "./Sections/AboutUs.jsx";
import Jobs from "./Sections/Jobs";
import Map from "./Sections/Map";
import SectionPills from "./Sections/SectionPills";
import Dashboard from "./Sections/Dashboard";
import "./style.css";
import Tracker from "./Sections/Tracker";
import { LocationOnOutlined } from "@material-ui/icons";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import CustomDropdown from "app/portal/Components/CustomDropdown/CustomDropdown.jsx";
import { Link } from "react-router-dom";

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
            <>
            <List className={classes.list}>
              <ListItem className={classes.listItem}>
                <Button
                  color="transparent"
                  className={classes.navLink}
                  onClick={() => this.handleClickOpen("classicModal")}
                >
                  <LocationOnOutlined className={classes.icons} /> Tracking
                </Button>
                <div className={classes.qlinks}>
                  <ul className={classes.ul}>
                    <li className={classes.li}><i className="fa fa-phone"></i>&nbsp; 91-9500302626</li>
                    <li className={classes.li}><i className="fa fa-envelope"></i>&nbsp; godeliverncr@gmail.com</li>
                  </ul>
                </div>
              </ListItem>
            </List>
            </>
          }
          rightLinksMobile={
            <>
            <List className={classes.list}>
              <ListItem className={classes.listItem}>
                <Button
                  color="transparent"
                  className={classes.navLink}
                  onClick={() => this.handleClickOpen("classicModal")}
                >
                  <LocationOnOutlined className={classes.icons} /> Tracking
                </Button>
                </ListItem>
                <ListItem className={classes.listItem}>
                <div className={classes.qlinks}>
                  <ul className={classes.ul}>
                    <li className={classes.li}><i className="fa fa-phone"></i>&nbsp; 91-9500302626</li>
                    <li className={classes.li}><i className="fa fa-envelope"></i>&nbsp; godeliverncr@gmail.com</li>
                  </ul>
                </div>
                </ListItem>
            </List>
            </>
          }
          leftLinks={
            <>
            <List className={classes.list}>
            <ListItem className={classes.listItem}>
              <CustomDropdown
                left
                hoverColor="info"
                dropdownHeader="Locations"
                buttonIcon="map"
                buttonProps={{
                  className: classes.navLink,
                  color: "transparent"
                }}
                dropdownList={[
                  
                  { divider: true },
                    <Link to="/godeliverind-page" className={classes.dropdownLink}>
                      Go Deliver IND
                    </Link>,
                  { divider: true },
                ]}
              />
            </ListItem>
          </List>
          </>
          }
          leftLinksMobile={
            <>
            <List className={classes.list}>
            <ListItem className={classes.listItem}>
              <CustomDropdown
                left
                buttonText="Switch"
                hoverColor="info"
                dropdownHeader="Locations"
                buttonIcon="map"
                buttonProps={{
                  className: classes.navLink,
                  color: "transparent"
                }}
                dropdownList={[
                  
                  { divider: true },
                    <Link to="/godeliverind-page" className={classes.dropdownLink}>
                      Go Deliver IND
                    </Link>,
                  { divider: true },
                ]}
              />
            </ListItem>
          </List>
          </>
          }
          fixed
          changeColorOnScroll={{
            height: 400,
            color: "godeliver"
          }}
          {...rest}
        />
        {this.state.classicModal &&(
          <Tracker parentState={this.state.classicModal} onModalClose= {this.onModalClose}/>
          )
        }
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
            {/*<SectionCarousel />*/}
            {/*<TeamSection />*/}
            <Map />
            <AboutUs />
            <Jobs />
            {/*<ContactUs />*/}
          </div>
        </div>
        <Footer />
      </div>
    );
  }
}

export default withStyles(landingPageStyle)(HomePage);
