/*eslint-disable*/
import React from "react";

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";
// @material-ui/icons
import GridContainer from "app/portal/Components/Grid/GridContainer.jsx";
import GridItem from "app/portal/Components/Grid/GridItem.jsx";
import Button from "app/portal/Components/CustomButtons/Button.jsx";
// core components
import downloadStyle from "app/assets/jss/material-kit-react/views/componentsSections/downloadStyle.jsx";

class AboutUs extends React.Component {
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.section}>
        <div className={classes.container}>
          <GridContainer className={classes.textCenter} justify="center">
            <GridItem xs={12} sm={12} md={8}>
              <h2>About Us</h2>
              <h4>
                We Provide Fastest, Accurate Courier & Cargo Services.!
              </h4>
            </GridItem>
            <GridItem xs={12} sm={8} md={6}>

            </GridItem>
          </GridContainer>
          <br />
          <br />
          <GridContainer className={classes.textCenter} justify="center">
            <GridItem xs={12} sm={12} md={8}>
              <h2>Let's Get In Touch!</h2>
              <h4>
                Ready to start your service with us? That's great! Give us a call or send us an email and we will get back to you as soon as possible!
              </h4>
            </GridItem>
            <GridItem xs={12} sm={8} md={6}>
            </GridItem>
          </GridContainer>
          <div className={classes.textCenter + " " + classes.sharingArea}>
            <GridContainer justify="center">
              <h3>Thank you for supporting us!</h3>
            </GridContainer>
            <Button color="primary" href="https://www.linkedin.com/in/gklogisticsservices-gkls-20734a142/"
              target="_blank">
              <i className={classes.socials + " fab fa-linkedin"} /> Linkedin
            </Button>
            <Button color="twitter" href="https://twitter.com/gkls_official/"
              target="_blank">
              <i className={classes.socials + " fab fa-twitter"} /> Tweet
            </Button>
            <Button color="facebook" href="https://www.facebook.com/gklsofficial/"
              target="_blank">
              <i className={classes.socials + " fab fa-facebook-square"} />{" "}
              Share
            </Button>
            <Button color="success" href="https://twitter.com/gkls_official/"
              target="_blank">
              <i className={classes.socials + " fab fa-whatsapp"} />
              Whatsapp
            </Button>
            <Button color="github">
              <i className={classes.socials + " fab fa-instagram"} /> Instagram
            </Button>
          </div>
        </div>
      </div>
    );
  }
}

export default withStyles(downloadStyle)(AboutUs);
